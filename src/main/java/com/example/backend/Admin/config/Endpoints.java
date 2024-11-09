/*
 * Author: Phạm Thái Sơn || JavaDEV
 * Facebook:https://www.facebook.com/son4k2
 * Github: https://github.com/SONPC-Developer
 * Youtube: https://www.youtube.com
 */

package com.example.backend.Admin.config;

public class Endpoints {
    public static String[] ADMIN_ENDPOINTS = {
            "/api/ecm/admin/auth/login",
            "/api/ecm/admin/auth/logout",
            "/api/ecm/admin/auth/get-otp",
            "/api/ecm/admin/auth/reset-password",
            "/api/ecm/admin/customers",
            "/api/v1/admin/items/product/**",
            "/api/admin/employees",
            "/api/admin/employees/**",
            "/api/admin/vouchers/search",
            "/api/admin/vouchers",
            "/api/admin/promotions",
            "/staff/dashboard"
    };
}
