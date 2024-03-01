package org.enes.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtTokenManager {
    /**
     * 1. Kullanıcılara Token oluşturmak
     * 2. Gelen Token bilgisini doğrulama
     */

    private final String SECRETKEY = "söfas321F1AS5FA11d53as1d51wa1d5D11D35adsa";
    private final String ISSUER = "Java13Auth";
    private final Long EXDATE = 1000L * 40;

    public Optional<String> createToken(Long authId) {
        String token;
        try {
            token = JWT.create().
                    withAudience()
                    .withClaim("authId", authId)
                    .withIssuer(ISSUER)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXDATE))
                    .sign(Algorithm.HMAC512(SECRETKEY));
            return Optional.of(token);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<Long> validateToken(String token) {
        try {
            /**
             * Token doğrularken ve içinden bilgileri çekerken ilk olarak
             * 1- şifreleme algoritmasını kullanarak token verify edilmeli.
             * 2- bu doğrulama işleminde süresinin dolup dolmadığıda kontrol edilmeli
             * 3- sahipliği bizim mi
             * Bunlar okey ise token decode edilmiş oluyor. Sonrasında ise claim nesnelerinin içinden
             * bilgiler alınarak dönüş yapılır.
             */
            Algorithm algorithm = Algorithm.HMAC512(SECRETKEY);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT == null)
                return Optional.empty();

            Long authId = decodedJWT.getClaim("authId").asLong();
            return Optional.of(authId);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
}
