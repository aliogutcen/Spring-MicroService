package com.ali.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Date;
import java.util.Optional;

@ControllerAdvice
public class JwtTokenManager {
    private final Long exTime = 1000L * 60 * 15;
    private final String passwordKey = "${PASSWORD}";

    /**
     * 1.token oluşturma
     */
    public Optional<String> createToken(Long id) {
        String token = "";
        try {
            /**
             withClaim-> içinde key-value şeklinde bilgiler saklanır
             Bu bilgiler Payload olarak tutulur ve herkes tarafından görülür.
             Bu nedenle buraya özel bilgiler koyulmaz.
             withIssuer-> jwt'yi olusturan kişinin kimliğini tutmak için kullanılır.
             withIssuerAt -> jwt'yi oluşturma zamanı
             withExpiresDate-> jwt'nin geçerlilik süresi 30sn - sonsuz
             sign-> hazırlanan içeriğinin imzalanması yapılır bunun için bir şifre belirlenir
             ve bununla kriptolama yapılır.
             **/

            token = JWT.create().withAudience()
                    .withClaim("id", id)
                    .withClaim("howtopage", "AUTHPAGE")
                    .withClaim("yetki", "ADMIN")
                    .withIssuer("Java5")
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + exTime))
                    .sign(Algorithm.HMAC512(passwordKey));
        } catch (Exception exception) {
            return Optional.empty();
        }

        return Optional.of(token);
    }

    /**
     * Token doğrulanması
     */
    public Boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(passwordKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("Java5").build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT == null) return false;
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    public Optional<Long> decodeToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(passwordKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("Java5").build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT == null) Optional.empty();
            return Optional.of(decodedJWT.getClaim("id").asLong());
        } catch (Exception exception) {
            return Optional.empty();
        }

    }

}
