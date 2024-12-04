/*
 * Author: Phạm Thái Sơn || JavaDEV
 * Facebook:https://www.facebook.com/son4k2
 * Github: https://github.com/SONPC-Developer
 * Youtube: https://www.youtube.com
 */

package com.example.backend.Admin.config;

public class Endpoints {
    /**
     * Endpoints không cần xác thực
     * */
    public static String[] PUBLIC_ADMIN_ENDPOINTS = {
            "/api/ecm/admin/auth/login",
            "/api/ecm/admin/auth/logout",
            "/api/ecm/admin/auth/get-otp",
            "/api/ecm/admin/auth/reset-password",
            "/api/ecm/admin/auth/refresh-token"
    };

    /**
     * Endpoints cần xác thực phía admin
     */
    public static String[] ADMIN_ENDPOINTS = {
            "/api/ecm/admin/**",
            "/api/ecm/admin/customers",
            "/api/ecm/admin/customers/**",
            "/api/ecm/admin/customers/search",
            "/api/ecm/admin/personal/**",
            "/api/ecm/admin/address/customer/**",
            "/api/ecm/admin/address/default/**",
            "/api/ecm/admin/address/**",
            "/api/orders/pending",
            "/api/v1/admin/products",
            "/api/v1/admin/products/**",
            "/api/v1/admin/product-variants/product/**",
            "/api/v1/admin/product-variants/promotion/**",
            "/api/admin/employees",
            "/api/admin/employees/**",
            "/api/v1/admin/vouchers",
            "/api/v1/admin/vouchers/**",
            "/api/v1/admin/vouchers/search",
            "/api/v1/admin/promotions",
            "/api/v1/admin/promotions/**"
    };
}
