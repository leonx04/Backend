package com.example.backend.Library.security;

import com.example.backend.Library.security.customer.CustomerDetailService;
import com.example.backend.Library.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Khai báo JwtUtil và UserDetailsService để xử lý việc kiểm tra và lấy thông tin người dùng
    private final JwtUtil jwtUtil;
    @Autowired
    @Qualifier("customerDetailService")
    private CustomerDetailService customerDetailService;

    // Constructor để khởi tạo các thành phần cần thiết
    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomerDetailService  customerDetailService) {
        this.jwtUtil = jwtUtil;
        this.customerDetailService = customerDetailService;
    }

    // Phương thức thực hiện kiểm tra và xác thực JWT trên mỗi request
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Lấy giá trị của header "Authorization" từ request
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Kiểm tra xem header Authorization có chứa JWT hay không (bắt đầu với "Bearer ")
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Lấy JWT từ header
            username = jwtUtil.extractUsername(jwt); // Trích xuất tên người dùng từ JWT
        }

        // Kiểm tra xem username có tồn tại và user chưa được xác thực trong SecurityContext
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Lấy thông tin chi tiết của người dùng từ UserDetailsService
            UserDetails userDetails = this.customerDetailService.loadUserByUsername(username);

            // Kiểm tra xem token có hợp lệ không
            if (jwtUtil.validateToken(jwt, userDetails)) {
                // Tạo đối tượng UsernamePasswordAuthenticationToken để xác thực người dùng
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                // Thiết lập thông tin chi tiết về request hiện tại
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Đặt thông tin xác thực vào SecurityContext
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        // Chuyển request tiếp tục qua các filter khác
        filterChain.doFilter(request, response);
    }
}
