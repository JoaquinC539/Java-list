package com.project.utils;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.time.LocalDate;
import com.project.models.Usersj;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

@SuppressWarnings("deprecation")
public class JwtUtil {
    public static String generateAuthToken(Usersj user) {
        try {
            String jwtKey = null;
            File envFile = new File("./.env");
            if (!envFile.exists() && !envFile.isFile()) {
                jwtKey = System.getenv("JWT");

            } else {
                Dotenv dotenv = Dotenv.load();
                jwtKey = dotenv.get("JWT");
            }
            if (jwtKey == null) {
                return null;
            }
            Map<String,Object> claim=new HashMap<>();
            claim.put("id", user.get_id());
            claim.put("username", user.getUsername());
            claim.put("created_at", user.getCreated_at());
            claim.put("updated_at", user.getUpdated_at());
            claim.put("type","auth");
            claim.put("generated", LocalDate.now().toString());
            JwtBuilder builder = Jwts.builder();
            builder.subject(TypeConverter.convertToString(user.get_id()));
            builder.claims(claim);           
            builder.signWith(SignatureAlgorithm.HS256, jwtKey);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            builder.setExpiration(calendar.getTime());
            return builder.compact();
        } catch (Exception e) {
            System.err.println("There was an error sigining the jwt: " + e);
            return null;
        }

    }
    public static String generateRefreshToken(Usersj user) {
        try {
            String jwtKey = null;
            File envFile = new File("./.env");
            if (!envFile.exists() && !envFile.isFile()) {
                jwtKey = System.getenv("REFRESH_JWT");

            } else {
                Dotenv dotenv = Dotenv.load();
                jwtKey = dotenv.get("REFRESH_JWT");
            }
            if (jwtKey == null) {
                return null;
            }
            Map<String,Object> claim=new HashMap<>();
            claim.put("id", user.get_id());
            claim.put("username", user.getUsername());
            claim.put("created_at", user.getCreated_at());
            claim.put("updated_at", user.getUpdated_at());
            claim.put("generated",LocalDate.now().toString());
            claim.put("type","refresh");
            

            JwtBuilder builder = Jwts.builder();
            builder.subject(TypeConverter.convertToString(user.get_id()));
            builder.claims(claim);           
            builder.signWith(SignatureAlgorithm.HS256, jwtKey);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            builder.setExpiration(calendar.getTime());
            return builder.compact();
        } catch (Exception e) {
            System.err.println("There was an error sigining the jwt: " + e);
            return null;
        }

    }

    public static Jws<Claims> descipherAuthToken(HttpServletRequest request) {
        try {
            LinkedHashMap<String, String> cookiesMap = RequestParser.parseCookies(request);
            String jwtKey = null;
            File envFile = new File("./.env");
            if (!envFile.exists() && !envFile.isFile()) {
                jwtKey = System.getenv("JWT");

            } else {
                Dotenv dotenv = Dotenv.load();
                jwtKey = dotenv.get("JWT");
            }
            if (jwtKey == null) {
                return null;
            }
            String rawJwtToken = cookiesMap.get("Auth");
            if (rawJwtToken == null) {
                return null;
            }            

            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(jwtKey)
                    .build()
                    .parseClaimsJws(rawJwtToken);

            return claimsJws;
        } catch (Exception e) {
            System.err.println("Error at decoding auth token: " + e);
            return null;
        }
    }
    public static Jws<Claims> descipherRefreshToken(HttpServletRequest request) {
        try {
            LinkedHashMap<String, String> cookiesMap = RequestParser.parseCookies(request);
            String jwtKey = null;
            File envFile = new File("./.env");
            if (!envFile.exists() && !envFile.isFile()) {
                jwtKey = System.getenv("REFRESH_JWT");

            } else {
                Dotenv dotenv = Dotenv.load();
                jwtKey = dotenv.get("REFRESH_JWT");
            }
            if (jwtKey == null) {
                return null;
            }
            String rawJwtToken = cookiesMap.get("refreshAuth");
            if (rawJwtToken == null) {
                return null;
            }            

            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(jwtKey)
                    .build()
                    .parseClaimsJws(rawJwtToken);

            return claimsJws;
        } catch (Exception e) {
            System.err.println("Error at decoding auth token: " + e);
            return null;
        }
    }
}
