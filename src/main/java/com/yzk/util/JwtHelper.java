package com.yzk.util;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * <pre>
 *     author : khum
 *     time   : 2018/9/9
 *     desc   :
 * </pre>
 */
public class JwtHelper {

    /**
     * 解析jwt
     * @param jsonWebToken
     * @param base64Security
     * @return
     */
    public static Claims parseJwt(String jsonWebToken,String base64Security){
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                .parseClaimsJws(jsonWebToken)
                .getBody();
    }


    /**
     * 构建jwt
     * @param name
     * @param userId
     * @param role
     * @param audience
     * @param issuer
     * @param TTLMillis
     * @param base64Security
     * @return
     */
    public static String createJwt(String name, Integer userId, String role,
                                   String audience, String issuer, long TTLMillis,
                                   String base64Security){
        SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, algorithm.getJcaName());

        JwtBuilder jwtBuilder = Jwts.builder().setHeaderParam("typ", "JWT")
                .claim("role", role)
                .claim("unique_name", name)
                .claim("userid", userId)
                .setIssuer(issuer)
                .setAudience(audience)
                .signWith(algorithm, signingKey);

        //设置Token过期时间
        if (TTLMillis >= 0) {
            long expMillis = nowMillis + TTLMillis;
            jwtBuilder.setExpiration(new Date(expMillis)).setNotBefore(now);
        }
        return jwtBuilder.compact();
    }


}
