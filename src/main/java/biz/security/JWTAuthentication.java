package biz.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Component
public class JWTAuthentication {

    private static final String SECRET_KEY = "erp_api!";

    /**
     * JWT 토큰을 생성한다.
     * @param subject
     * @param ttlMillis
     * @return
     */
    public String createToken(String subject, long ttlMillis) {
        if(ttlMillis < 0){
            throw new RuntimeException("토근 유효시간 오류 :::  {} " +ttlMillis);
        }

        // key 값 암호화 hs-256
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
        return Jwts.builder()
                .setSubject(subject)
                .signWith(signatureAlgorithm, signingKey)
                .setExpiration(new Date(System.currentTimeMillis()+ttlMillis))
                .compact();
    }

    /**
     * JWT 생선된 토큰 유효값
     * @param token
     * @return
     */
    public String getSubject(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    /**
     * JWT 토큰값으로 회원 정보 추출
     * @param token
     * @param key
     * @return
     */
    public String getUserInformation(String token, String key) {
        String value = Jwts.parser()
                .setSigningKey((DatatypeConverter.parseBase64Binary(SECRET_KEY)))
                .parseClaimsJwt(token)
                .getBody()
                .get(key, String.class);
        return value;
    }

    /**
     * JWT RequestHeader 토큰값을 가져온다
     * @param request
     * @return
     */
    public String getToken(HttpServletRequest request) {
        if(request.getHeader("Authorization") != null )
            return request.getHeader("Authorization").substring(7);
        return "invalid token";
    }

}
