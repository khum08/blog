package com.yzk.util;

import com.yzk.exception.AccessException;
import com.yzk.exception.ExceptionEnum;
import com.yzk.model.domain.User;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

/**
 * <pre>
 *     author : khum
 *     time   : 2018/9/9
 *     desc   : TOKEN生成和解析的帮助类
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
        try {
            return Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jsonWebToken)
                    .getBody();
        }catch (ExpiredJwtException e){
            throw new AccessException(ExceptionEnum.HTTP_TOKEN_EXPIRED);
        }catch (SignatureException e){
            throw new AccessException(ExceptionEnum.HTTP_TOKEN_SIGNATURE);
        }catch (IllegalArgumentException e){
            throw new AccessException(ExceptionEnum.HTTP_TOKEN_ARGS);
        }catch (Exception e){
            throw new AccessException(ExceptionEnum.HTTP_TOKEN_ERROR);
        }
    }


    /**
     * 构建jwt
     * @param user
     * @param audience
     * @param issuer
     * @param TTLMillis
     * @param base64Security
     * @return
     */
    public static String createJwt(User user,
                                   String audience, String issuer, long TTLMillis,
                                   String base64Security){
        SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, algorithm.getJcaName());

        JwtBuilder jwtBuilder = Jwts.builder().setHeaderParam("typ", "JWT")
                .claim("role", user.getAuthorities())
                .claim("username", user.getUsername())
                .claim("is_enabled", user.isEnabled())
                .claim("email",user.getEmail())
                .claim("userid", user.getId())
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
