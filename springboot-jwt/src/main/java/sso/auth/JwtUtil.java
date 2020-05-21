package sso.auth;
/*
 * @author chou
 * @Description
 * @since 2019-05-17
 **/

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;

import javax.servlet.http.HttpServletRequest;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {

    private static final String jwtCookieName = "iplat-jwt";

    public static String generateToken(String subject) {

//        Key key = Keys.hmacShaKeyFor(keyBytes);
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "jwt")
                                 .setSubject(subject)//授体(对应系统中的登录用户)
                                 .setId(UUID.randomUUID().toString())//唯一标示
                                 .setIssuer("issuer_jason")//发行方
                                 .setIssuedAt(now)//发行日期
                                 .claim("user", "user_jason")//声明
                                 .claim("postId", "00000001")//声明
                                 .signWith(SignatureAlgorithm.RS256, getSecKey());
        return builder.compact();
    }

    public static String getSubject(HttpServletRequest request) {
        String token = CookieUtil.getValue(request, jwtCookieName);
        if (token == null) return null;

        return Jwts.parser().setSigningKey(getPubKey()).parseClaimsJws(token).getBody().getSubject();
    }

    public static String getSubject(String token) {

        if (token == null) return null;

        return Jwts.parser().setSigningKey(getPubKey()).parseClaimsJws(token).getBody().getSubject();
    }

//    public static String getPubKey() {
//        return pubKey;
//    }
//
//    public static String getSecKey() {
//        return secKey;
//    }

    public static String getJwtCookieName() {
        return jwtCookieName;
    }

    public static Key getPubKey() {
        PublicKey publicKey;
        try {
            String pubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAugOe5CaWE2sYvt+RJYoY/M6zLbF63xfLXYPMK8J4nLM6/TSDh/JqdkffeszxxieIFkBsew0YY8VzuqA9acNYnbEe8j5JeQLXZlIHQxmYmCXQ4qt6ep457l1hXZsdRhNNrwUTeDk2F5WCBV4xqF9o22SVCsoWslj9BPvbPFllBqMZv4DSt7w60trdFDvM+t9hbTDMU0U1dFrs5SYeli8mMq2ZpglrXqacB6MFp1wEKb0+Zp9KbFpZAxvTYJRYIc/MA8LA98rgdixFrIuAHmVxRLpNEUsACVYNPBhmiu8cbptRDE32RzykAKAsWdXBNBXkxRXZMCESo2PrGVkwEfv7GwIDAQAB";
            byte[] keyBytes = Decoders.BASE64.decode(pubKey);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");//使用rsa对称加密
            publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            publicKey = null;
        }
        return publicKey;
    }

    private static Key getSecKey() {
        PrivateKey privateKey;
        try {
            String secKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC6A57kJpYTaxi+35Elihj8zrMtsXrfF8tdg8wrwnicszr9NIOH8mp2R996zPHGJ4gWQGx7DRhjxXO6oD1pw1idsR7yPkl5AtdmUgdDGZiYJdDiq3p6njnuXWFdmx1GE02vBRN4OTYXlYIFXjGoX2jbZJUKyhayWP0E+9s8WWUGoxm/gNK3vDrS2t0UO8z632FtMMxTRTV0WuzlJh6WLyYyrZmmCWteppwHowWnXAQpvT5mn0psWlkDG9NglFghz8wDwsD3yuB2LEWsi4AeZXFEuk0RSwAJVg08GGaK7xxum1EMTfZHPKQAoCxZ1cE0FeTFFdkwIRKjY+sZWTAR+/sbAgMBAAECggEAZ2KlyDauCOLoYQYPPScRFkmoCshu+1nVPQeIzfWLZcS0D5FFXaTdiRoPWshXxIOqPnvWD1pK6QEQfnSbTZI2SYbtJUR7P5fVslf7E09Boaa6/5DBRS92EUazW/xfe9g0wAb5OEPMDbWaC3O789H7jJzNva5uiPeuwTteMZ+Z5+KCQqSVa+tvumor1ALICOgPkivfo6Lh9eGy5Z6lS3+inxl50EE+0ZfbO0tyjWD2v+xgzM2n+9Tk3veWVjiHI2EW/Tqt4aY61KfofvFsDQydQCJx+CpkzzV+E38djkyLvUNbGGiRkhXsq9sYwtu8zFmIhWAY7IIOf2FcNOKi10ShMQKBgQDfpFAjSvVpHahUiZY6+6zVi0eKlROd1aZyjS9PwQyZIVBpuPRG0a0Q6IKZSgc5cXhfSCX9587Voxdk1Z8ZisDuIRj7LUCuDbV+8Nw/1HCtNiWvdpzLNjsU8hIYY95AQzKUt2cM94Z1Qf+ZiOMVMD/tRyFPkxFyvbLp3E21VufUGQKBgQDU7ZQiCxYkJ+zq/ZEOCehcbUTetLHtzsQL+n44PyJE4jfz5pEsxIjVmtEwam9g4HZJ0aj6syKnyfE1LGb/24gZfSMSDRwOOJGsA2ETwVo+OMsub5MJJcSpjZBPOZktdVgwXGHea5FV9MfbQWw7qZsw+5tj+Tw31eSmiBQQDSLPUwKBgFwJiD1uDN9MqEjaMqxlhPSBFFkxDtim45BDEFR3M1MVxb0ss8MtaVcLCSxCrgRy8UHtNE1xB8seCnOiAo8Fbm7RyjEcp4C5/ZaXU10ldZWjMhEq9E4hLGFrWg0VlJLH1NolPFC20WwSVDmAf0vqBnUFD+1qrgSVkzrxQj293TyJAoGAaScu6aUJ+S8SbHZu5Tz1WHfJ9CXOXP+oh7XcEZD0Lop9UWEDPtWMMdj0B/H4MSKIqCn3Rl+i9LPg//5+m0piKxxGboq02T0Mi6NblLL/Cw1nIfaN6Y2SUp2Lr+RlBjMWK0PkPUBoFMf+zwO/HLPE5WrMMZILMBPciHVZWxfxoesCgYAkjqFNB9qzcvepA7RqcSBRAJOisOO2QdaGKS44i6yxG1bQU9U14InmlGnjKD3rL960aCRP2OjN7FKmHnrWPHsppu4esmBhtQrDSit1M/xNlk7mSwD5l/3Rxb/QWclHIXysV7tvkEGBLr0jBQW+Gc5MiSi/1kM1pbBkZNEnY470wg==";
            byte[] keyBytes = Decoders.BASE64.decode(secKey);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");//使用rsa对称加密
            privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            privateKey = null;
        }
        return privateKey;
    }
}
