package com.courses.zonelearn.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class JWTProvider {

    @Value("${security.token.secret}")
    private String secretKey;

    public String validateToken(String token) {
        token = token.replace("Bearer ", "");

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        try {
            return JWT.require(algorithm).build().verify(token).getSubject();
        } catch (JWTVerificationException exception) {
            exception.printStackTrace();
            return "";
        }
    }

    public String getRoleFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        DecodedJWT decodedJWT = JWT.require(algorithm)
                .withIssuer("@zonelearn")
                .build()
                .verify(token.replace("Bearer ", ""));

        return decodedJWT.getClaim("role").asString();
    }

    public String getSubFromJwt(String token) {
        String[] parts = token.split("\\.");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid JWT Token");
        }

        String payload = parts[1];

        String decodedPayload = new String(Base64.getUrlDecoder().decode(payload));

        JSONObject json = new JSONObject(decodedPayload);
        return json.getString("sub");
    }

}
