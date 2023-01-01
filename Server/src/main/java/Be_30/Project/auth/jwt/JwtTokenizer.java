package Be_30.Project.auth.jwt;

import Be_30.Project.auth.jwt.refreshtoken.entity.RefreshToken;
import Be_30.Project.auth.jwt.refreshtoken.repository.RefreshTokenRepository;
import Be_30.Project.exception.BusinessLogicException;
import Be_30.Project.exception.ExceptionCode;
import Be_30.Project.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**인증 성공 후 jwt를 생성 및 발급
클라이언트의 요청이 들어올 때 마다 전달된 jwt를 검증하는 역할
생성 - 발급 - 검증*/

@Component
@Getter
public class JwtTokenizer {

    @Getter
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Getter
    @Value("${jwt.access-token-expiration-minutes}")
    private int accessTokenExpirationMinutes;

    @Getter
    @Value("${jwt.refresh-token-expiration-minutes}")
    private int refreshTokenExpirationMinutes;


    private final RefreshTokenRepository refreshTokenRepository;

    private final MemberRepository memberRepository;

    public JwtTokenizer(RefreshTokenRepository refreshTokenRepository,
        MemberRepository memberRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * secretkey를 암호화한다.
     */
    public String encodeBase64SecretKey(String secretKey) {

        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));

    }


    /** 액세스토큰 생성
     *
     * @param claims 인증된 사용자 정보
     * @param subject 토근의 제목
     * @param expiration 만료기간
     * @param base64EncodedSecretKey 사인할 key
     * @return
     */
    public String generateAccessToken(Map<String, Object> claims,
        String subject,
        Date expiration,
        String base64EncodedSecretKey) {

        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(Calendar.getInstance().getTime())
            .setExpiration(expiration)
            .signWith(key)
            .compact();
    }

    public String generateRefreshToken(String subject, Date expiration, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        return Jwts.builder()
            .setSubject(subject)
            .setIssuedAt(Calendar.getInstance().getTime())
            .setExpiration(expiration)
            .signWith(key)
            .compact();
    }


    public Jws<Claims> getClaims(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        Jws<Claims> claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(jws);
        return claims;
    }

    public void verifySignature(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(jws);
    }


    public Date getTokenExpiration(int expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationMinutes);
        Date expiration = calendar.getTime();

        return expiration;
    }

    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return key;
    }

    public void saveRefreshToken(String refreshToken, String email, long tokenId){
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findById(tokenId);

        //optionalRefreshToken.ifPresent(refreshTokenRepository::delete);

        refreshTokenRepository.save(new RefreshToken(refreshToken,email,tokenId));
    }

//    public void verifiedRefreshToken(String refreshToken){
//        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByTokenValue(refreshToken);
//        if(!optionalRefreshToken.isPresent())
//            throw new BusinessLogicException(ExceptionCode.TOKEN_NOT_FOUND);
//    }
    


}
