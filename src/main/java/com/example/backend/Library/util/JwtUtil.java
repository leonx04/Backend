//package com.example.backend.Library.util;
//
//import io.github.cdimascio.dotenv.Dotenv;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import jakarta.annotation.PostConstruct;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//@Service
//public class JwtUtil {
//
//    // Biến chứa khóa bí mật dùng để ký JWT
//    private String SECRET_KEY;
//
//    // Phương thức khởi tạo, lấy khóa bí mật từ file .env
//    @PostConstruct
//    public void init() {
//        Dotenv dotenv = Dotenv.load();
//        this.SECRET_KEY = dotenv.get("JWT_SECRET");
//    }
//
//    // Phương thức trích xuất tên người dùng từ token JWT
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    // Phương thức trích xuất thời hạn hết hạn của token JWT
//    public Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    // Phương thức chung để trích xuất các thông tin (claims) từ token JWT
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    // Phương thức lấy tất cả các thông tin (claims) từ token JWT
//    private Claims extractAllClaims(String token) {
//        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
//    }
//
//    // Phương thức kiểm tra token đã hết hạn hay chưa
//    private Boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    // Phương thức tạo token JWT từ thông tin của người dùng
//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//        return createToken(claims, userDetails.getUsername());
//    }
//
//    // Phương thức xây dựng token JWT với các thông tin (claims) và subject (username)
//    private String createToken(Map<String, Object> claims, String subject) {
//        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Thời hạn token 10 giờ
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact(); // Ký token với thuật toán HS256
//    }
//
//    // Phương thức kiểm tra token có hợp lệ hay không (so sánh username và kiểm tra thời hạn)
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//}

package com.example.backend.Library.util;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private String SECRET_KEY;

    @PostConstruct
    public void init() {
        Dotenv dotenv = Dotenv.load();
        this.SECRET_KEY = dotenv.get("JWT_SECRET");
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claims != null ? claimsResolver.apply(claims) : null;
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            System.out.println("Invalid token: " + e.getMessage());
            return null;
        }
    }

    private Boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        return expiration != null && expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails, String name) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities().toArray()[0].toString());
        claims.put("username", name);
        System.out.println("Claims: " + claims);
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 giờ
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
