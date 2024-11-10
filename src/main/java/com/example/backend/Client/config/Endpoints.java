/*
 * Author: Phạm Thái Sơn || JavaDEV
 * Facebook:https://www.facebook.com/son4k2
 * Github: https://github.com/SONPC-Developer
 * Youtube: https://www.youtube.com
 */

package com.example.backend.Client.config;

public class Endpoints {
    public static String[] CLIENT_ENDPOINTS = {
            "/api/ecm/client/auth/login",
            "/api/ecm/client/auth/logout",
            "/api/ecm/client/auth/get-otp",
            "/api/ecm/client/auth/reset-password",
            "/api/ecm/client/products",
            "/api/ecm/client/products/**",
            "/api/ecm/client/promotions",
            "/api/ecm/client/promotions/**",
            "/api/ecm/client/vouchers",
            "/api/ecm/client/vouchers/**",
            "/api/ecm/client/orders",
            "/api/ecm/client/orders/**",
            "/api/ecm/client/customers",
            "/api/ecm/client/customers/**",
            "/api/ecm/client/employees",
            "/api/ecm/client/employees/**",
            "/api/ecm/client/dashboard"
    };
}
