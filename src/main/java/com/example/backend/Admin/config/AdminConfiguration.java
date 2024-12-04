/*
 * Author: Phạm Thái Sơn || JavaDEV
 * Facebook:https://www.facebook.com/son4k2
 * Github: https://github.com/SONPC-Developer
 * Youtube: https://www.youtube.com
 */

package com.example.backend.Admin.config;

import com.example.backend.Library.security.CorsConfig;
import com.example.backend.Library.security.auth.JwtAuthenticationFilter;
import com.example.backend.Library.security.employee.EmployeeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.backend.Admin.config.Endpoints.ADMIN_ENDPOINTS;
import static com.example.backend.Admin.config.Endpoints.PUBLIC_ADMIN_ENDPOINTS;
import static com.example.backend.Client.config.Endpoints.CLIENT_ENDPOINTS;
import static com.example.backend.Client.config.Endpoints.PUBLIC_CLIENT_ENDPOINTS;

@Configuration
@EnableWebSecurity
@Order(1)
public class AdminConfiguration {
    private final EmployeeDetailService employeeDetailService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final PasswordEncoder passwordEncoder;
    private final CorsConfig corsConfig;

    public AdminConfiguration(EmployeeDetailService employeeDetailService, PasswordEncoder passwordEncoder, JwtAuthenticationFilter jwtAuthenticationFilter, CorsConfig corsConfig) {
        this.employeeDetailService = employeeDetailService;
        this.passwordEncoder = passwordEncoder;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.corsConfig = corsConfig;
    }

    @Bean
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfig.corsConfigSource()))
                .securityMatcher("/api/ecm/admin/**", "/api/ecm/user/**", "/api/v1/admin/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_CLIENT_ENDPOINTS).permitAll()
                        .requestMatchers(CLIENT_ENDPOINTS).hasRole("USER")
                        .requestMatchers(PUBLIC_ADMIN_ENDPOINTS).permitAll()
                        .requestMatchers(ADMIN_ENDPOINTS).hasAnyRole("ADMIN", "STAFF")
                )
//                .formLogin(form -> form
//                        .loginPage("/api/ecm/admin/auth/login")
//                        .defaultSuccessUrl("/api/ecm/admin/auth/dashboard")
//                        .failureUrl("/api/ecm/admin/auth/login?error=true")
//                )
//                .rememberMe(remember -> remember
//                        .key("uniqueAndSecretKey")
//                        .tokenValiditySeconds(86400)
//                        .userDetailsService(employeeDetailService)
//                )
//                .exceptionHandling(exceptions -> exceptions
//                        .authenticationEntryPoint((request, response, authException) ->
//                                response.sendRedirect("/api/ecm/admin/auth/login"))
//                )
//                .exceptionHandling(exceptions -> exceptions // Nó sẽ trả về lỗi 401 khi không có quyền truy cập
//                        .authenticationEntryPoint((request, response, authException) -> {
//                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                            response.getWriter().write("Unauthorized access");
//                        })
//                )
//                // Xác thực bằng JWT
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .logout(logout -> logout
//                        .logoutRequestMatcher(new AntPathRequestMatcher("/api/ecm/admin/auth/logout"))
//                        .logoutSuccessUrl("/api/ecm/admin/auth/login?logout=true")
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                )
//                .sessionManagement(session -> session
//                        .maximumSessions(1)
//                        .maxSessionsPreventsLogin(false)
//                        .expiredUrl("/api/ecm/admin/auth/login?expired=true"))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(employeeDetailService)
                .passwordEncoder(passwordEncoder);
    }
}
