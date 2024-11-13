package com.example.backend.Admin.config;

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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.example.backend.Admin.config.Endpoints.ADMIN_ENDPOINTS;

@Configuration
@EnableWebSecurity
@Order(1)
public class AdminConfiguration {
    private final EmployeeDetailService employeeDetailService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final PasswordEncoder passwordEncoder;

    public AdminConfiguration(EmployeeDetailService employeeDetailService, PasswordEncoder passwordEncoder, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.employeeDetailService = employeeDetailService;
        this.passwordEncoder = passwordEncoder;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/api/ecm/admin/**", "/api/ecm/user/**")
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(ADMIN_ENDPOINTS).permitAll()
//                        .hasAnyRole("ADMIN", "STAFF")
                                .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/api/ecm/admin/login")
                        .loginProcessingUrl("/api/ecm/admin/login")
                        .defaultSuccessUrl("/api/ecm/admin/dashboard")
                        .failureUrl("/api/ecm/admin/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/api/ecm/admin/logout"))
                        .logoutSuccessUrl("/api/ecm/admin/login?logout=true")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .permitAll()
                )
                .rememberMe(remember -> remember
                        .key("uniqueAndSecretKey")
                        .tokenValiditySeconds(86400)
                        .userDetailsService(employeeDetailService)
                )
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect("/api/ecm/admin/login"))
                )
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .expiredUrl("/api/ecm/admin/login?expired=true"))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(employeeDetailService)
                .passwordEncoder(passwordEncoder);
    }
}