package com.example.backend.Client.config;

import com.example.backend.Library.security.JwtAuthenticationFilter;
import com.example.backend.Library.security.customer.CustomerDetailService;
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
    private final PasswordEncoder passwordEncoder;

    public ClientConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, CustomerDetailService customerDetailService, PasswordEncoder passwordEncoder) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customerDetailService = customerDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    // Bean để cấu hình AuthenticationManager, sử dụng dịch vụ chi tiết người dùng và bộ mã hóa mật khẩu
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        // Cấu hình AuthenticationManagerBuilder để sử dụng dịch vụ chi tiết người dùng và bộ mã hóa mật khẩu
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                // Sử dụng dịch vụ chi tiết người dùng
                .userDetailsService(customerDetailService)
                // Sử dụng bộ mã hóa mật khẩu
                .passwordEncoder(passwordEncoder)
                // Xây dựng AuthenticationManager
                .and()
                .build();
    }

    // Bean để cấu hình chuỗi bảo mật cho ứng dụng
    @Bean
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                // Vô hiệu hóa CSRF vì ta sử dụng token JWT
                .csrf(AbstractHttpConfigurer::disable)
                // Cấu hình CORS cho phép các yêu cầu từ nguồn khác
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Cấu hình quyền truy cập cho các URL
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/ecm/register", "/api/ecm/user/login",
                                "/api/ecm/auth/**",
                                "/api/ecm/admin/customers/search",
                                 "/api/ecm/user/**",
//                                "/api/ecm/admin/customers/fake-data",
                                "/api/ecm/admin/customers/**"
                        ).permitAll() // Cho phép truy cập không cần xác thực
                        .requestMatchers("/api/confirm-order/**", "/api/order/**", "/api/order-detail/**", "/api/admin/**").authenticated() // Yêu cầu xác thực
                        .anyRequest().authenticated() // Tất cả các yêu cầu khác đều cần xác thực
                )
                // Quản lý session ở chế độ STATELESS vì ta sử dụng token để xác thực
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Thêm bộ lọc JWT trước UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build(); // Xây dựng và trả về cấu hình bảo mật
    }

    // Bean để cấu hình CORS cho phép các yêu cầu từ nguồn khác với cấu hình cụ thể
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Chỉ cho phép các yêu cầu từ địa chỉ "http://127.0.0.1:5500"
        configuration.setAllowedOrigins(List.of("http://127.0.0.1:5500"));
        // Cho phép các phương thức HTTP: GET, POST, PUT, DELETE, OPTIONS
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Cho phép tất cả các header trong request
        configuration.setAllowedHeaders(List.of("*"));
        // Cho phép gửi thông tin xác thực (cookie, authorization header)
        configuration.setAllowCredentials(true);

        // Tạo và đăng ký cấu hình CORS cho tất cả các URL
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Đăng ký cấu hình CORS cho tất cả các URL
        source.registerCorsConfiguration("/**", configuration);
        return source; // Trả về cấu hình nguồn CORS
    }
}
