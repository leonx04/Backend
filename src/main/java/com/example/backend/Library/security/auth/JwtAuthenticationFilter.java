/*
 * Author: Phạm Thái Sơn || JavaDEV
 * Facebook:https://www.facebook.com/son4k2
 * Github: https://github.com/SONPC-Developer
 * Youtube: https://www.youtube.com
 */

package com.example.backend.Library.security.auth;

import com.example.backend.Library.security.customer.CustomerDetailService;
import com.example.backend.Library.security.employee.EmployeeDetailService;
import com.example.backend.Library.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    private final JwtUtil jwtUtil;
    private final CustomerDetailService customerDetailService;
    private final EmployeeDetailService employeeDetailService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil,
                                   @Qualifier("customerDetailService") CustomerDetailService customerDetailService,
                                   @Qualifier("employeeDetailService") EmployeeDetailService employeeDetailService) {
        this.jwtUtil = jwtUtil;
        this.customerDetailService = customerDetailService;
        this.employeeDetailService = employeeDetailService;
    }

    // Phương thức thực hiện kiểm tra và xác thực JWT trên mỗi request
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Lây token từ header
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) { // Kiểm tra xem token có bắt đầu bằng "Bearer " không
            // Lấy token từ header
            jwt = authorizationHeader.substring(7);
            // Lấy username từ token
            username = jwtUtil.extractUsername(jwt);
        }


        // Kiểm tra xem username có tồn tại và đã được xác thực chưa
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = null;
            System.out.println(request.getRequestURI());

            // Phân biệt khách hàng và nhân viên dựa trên URL
            if (request.getRequestURI().contains("/api/ecm/admin/") || request.getRequestURI().contains("/admin/")) {
                userDetails = employeeDetailService.loadUserByUsername(username);
                System.out.println("em");
            } else if (request.getRequestURI().contains("/api/ecm/user/")) {
                userDetails = customerDetailService.loadUserByUsername(username);
                System.out.println("cus");
            }

            // Kiểm tra xem token có hợp lệ không
            if (userDetails != null && jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}

