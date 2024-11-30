/*
 * Author: Phạm Thái Sơn || JavaDEV
 * Facebook:https://www.facebook.com/son4k2
 * Github: https://github.com/SONPC-Developer
 * Youtube: https://www.youtube.com
 */

package com.example.backend.Admin.config;

public class Endpoints {
    public static String[] PUBLIC_ADMIN_ENDPOINTS = {
            "/api/ecm/admin/auth/login",
            "/api/ecm/admin/auth/logout",
            "/api/ecm/admin/auth/get-otp",
            "/api/ecm/admin/auth/reset-password",
            "/api/ecm/admin/auth/refresh-token"
    };

    public static String[] ADMIN_ENDPOINTS = {
            "/api/ecm/admin/customers",
            "/api/ecm/admin/customers/**",
            "/api/ecm/admin/customers/search",
            "/api/ecm/admin/personal/**",
            "/api/ecm/admin/address/customer/**",
            "/api/orders/pending",
            "/api/v1/admin/products",
            "/api/v1/admin/products/**",
            "/api/admin/employees",
            "/api/admin/employees/**",
            "/api/admin/vouchers/search",
            "/api/admin/vouchers",
            "/api/v1/admin/promotions",
            "/staff/dashboard"
    };
}
