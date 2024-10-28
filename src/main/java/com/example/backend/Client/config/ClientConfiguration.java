package com.example.backend.Client.config;

import com.example.backend.Library.security.JwtAuthenticationFilter;
import com.example.backend.Library.security.customer.CustomerDetailService;
import com.example.backend.Library.security.employee.EmployeeDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Order(2)
public class ClientConfiguration {

    // Khai báo các thành phần cần thiết: bộ lọc JWT, dịch vụ chi tiết người dùng và bộ mã hóa mật khẩu
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomerDetailService customerDetailService;
    private final EmployeeDetailService employeeDetailService;
    private final PasswordEncoder passwordEncoder;

    public ClientConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, CustomerDetailService customerDetailService, EmployeeDetailService employeeDetailService, PasswordEncoder passwordEncoder) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customerDetailService = customerDetailService;
        this.employeeDetailService = employeeDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    // Bean để cấu hình AuthenticationManager, sử dụng dịch vụ chi tiết người dùng và bộ mã hóa mật khẩu
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        // Cấu hình AuthenticationManagerBuilder để sử dụng dịch vụ chi tiết người dùng và bộ mã hóa mật khẩu
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customerDetailService)
                .passwordEncoder(passwordEncoder)
                .and()
                .userDetailsService(employeeDetailService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    // Bean để cấu hình chuỗi bảo mật cho ứng dụng
    @Bean
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Vô hiệu hóa CSRF vì ta sử dụng token JWT
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Cấu hình CORS
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/ecm/user/login", "/api/ecm/register", "/api/ecm/admin/login", "/admin/forgot-password").permitAll() // Cho phép truy cập không cần xác thực với các endpoint đăng ký và đăng nhập
//                        .requestMatchers("/api/ecm/admin/**").hasRole("ADMIN") // Chỉ cho phép ADMIN truy cập các endpoint admin
//                        .requestMatchers("/admin/dashboard").hasAnyRole("ADMIN", "STAFF") // Cho phép ADMIN và STAFF truy cập dashboard
//                        .anyRequest().authenticated() // Các yêu cầu còn lại đều cần xác thực
                                .anyRequest().permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Quản lý session ở chế độ STATELESS
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Thêm bộ lọc JWT trước UsernamePasswordAuthenticationFilter

        return http.build(); // Xây dựng và trả về cấu hình bảo mật
    }

    // Bean để cấu hình CORS cho phép các yêu cầu từ nguồn khác với cấu hình cụ thể
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://127.0.0.1:5500")); // Chỉ cho phép các yêu cầu từ địa chỉ "http://127.0.0.1:5500"
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Cho phép các phương thức HTTP
        configuration.setAllowedHeaders(List.of("*")); // Cho phép tất cả các header trong request
        configuration.setAllowCredentials(true); // Cho phép gửi thông tin xác thực

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Đăng ký cấu hình CORS cho tất cả các URL
        return source;
    }
}
