package com.example.linkedin.Utils;
import com.sun.net.httpserver.HttpExchange;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.Key;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;


public class JwtController {
    private static final Key key = Keys.hmacShaKeyFor("RezEsfand@13842005-MeytiBaser-LINKED-IN".getBytes(StandardCharsets.UTF_8));
    private static final long EXPIRATION_TIME = 1_000 * 60 * 60  * 3; // 3 hour
    private static final SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
  //  private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Base64.getDecoder().decode("RezEsfand@13842005-MeytiBaser-LINKED-IN"));

    public static String createToken(String email) {
        String token = Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, algorithm)
                .compact();
        return token;
    }

    public static String verifyToken(HttpExchange exchange) {
        try {
            String token = exchange.getRequestHeaders().get("JWT").get(0);
            return Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {
            return null;
        }
    }
//    public static String getEmailFromToken(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(SECRET_KEY)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//
//        return claims.get("email", String.class);
//    }

}
