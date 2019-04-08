package com.dhm.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.impl.PublicClaims;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.time.DateUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author duhongming
 * @Email 19919902414@189.cn
 * @Date 2018/12/7 18:36
 */
public class JwtHelper {
    private static final String SECRET = "session_secret";
    private static final String ISSUER = "clw";
    private static final String SUBUER = "e-app";
    private static final String AUDUER = "consumer";


    public static String genToken(Map<String, String> claims) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            /**
             * iss: jwt签发者
             * sub: jwt所面向的用户
             * aud: 接收jwt的一方
             * exp: jwt的过期时间，这个过期时间必须要大于签发时间
             * nbf: 定义在什么时间之前，该jwt都是不可用的.
             * iat: jwt的签发时间
             * jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
             */
            JWTCreator.Builder builder = JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(SUBUER)
                    .withAudience(AUDUER)
                    .withJWTId(UUID.randomUUID().toString())
                    .withNotBefore(DateUtils.addDays(new Date(), -1))
                    .withIssuedAt(new Date())
                    .withExpiresAt(DateUtils.addDays(new Date(), 1));
            claims.forEach((k, v) -> builder.withClaim(k, v));
            return builder.sign(algorithm);
        } catch (IllegalArgumentException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> verifyToken(String token) {
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256(SECRET);
        } catch (IllegalArgumentException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
        DecodedJWT jwt = verifier.verify(token);
        Map<String, Claim> map = jwt.getClaims();
        Map<String, Object> resultMap = new HashMap<>();
        map.forEach((k, v) -> {
            if (k.equals(PublicClaims.EXPIRES_AT) || k.equals(PublicClaims.ISSUED_AT) || k.equals(PublicClaims.NOT_BEFORE)) {
                resultMap.put(k, v.asDate());
            } else {
                resultMap.put(k, v.asString());
            }
        });

        return resultMap;
    }

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("userId","007");
        map.put("userName", "duhongming");
        String token = genToken(map);
        Map<String, Object> verifyMap = verifyToken(token);
    }
}