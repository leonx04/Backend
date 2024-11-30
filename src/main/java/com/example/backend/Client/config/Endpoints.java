/*
 * Author: Phạm Thái Sơn || JavaDEV
 * Facebook:https://www.facebook.com/son4k2
 * Github: https://github.com/SONPC-Developer
 * Youtube: https://www.youtube.com
 */

package com.example.backend.Client.config;

public class Endpoints {

    public static String[] PUBLIC_CLIENT_ENDPOINTS = {
            "/api/ecm/user/auth/login",
            "/api/ecm/user/auth/logout",
            "/api/ecm/user/auth/get-otp",
            "/api/ecm/user/auth/reset-password",
            "/api/ecm/user/auth/register",
            "/api/ecm/user/auth/refresh-token"
    };

    public static String[] CLIENT_ENDPOINTS = {
            "/api/ecm/user/products",
            "/api/ecm/user/products/**",
            "/api/ecm/user/promotions",
            "/api/ecm/user/promotions/**",
            "/api/ecm/user/vouchers",
            "/api/ecm/user/vouchers/**",
            "/api/ecm/user/orders",
            "/api/ecm/user/orders/**",
            "/api/ecm/user/customers",
            "/api/ecm/user/customers/**",
            "/api/ecm/user/addresses/**",
            "/api/ecm/user/addresses/customer/**",
            "/api/ecm/user/addresses/defaut/**"
    };
}
