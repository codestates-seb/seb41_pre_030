package Be_30.Project.auth.filter;

import Be_30.Project.auth.jwt.JwtTokenizer;
import Be_30.Project.auth.jwt.refreshtoken.repository.RedisRepository;
import Be_30.Project.auth.userdetails.MemberDetails;
import Be_30.Project.dto.LoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 로그인 시 인증정보 확인(AuthenticationManager가 UserDetailService를 호출하여 UserDetail을 확인) 후
 토큰 발급(jwtTokenizer)
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenizer jwtTokenizer;
    private final RedisRepository redisRepository;

    /**
     * 입력받은 username, password를 바탕으로 인증정보를 생성하는 로직
     * 1. ObjectMapper로 request에서 loginDto로 username,password를 담는다.
     * 2. 이 정보를 가지고 '인증 전'(권한 설정이 되어 있지 않은) authenticationToken 발급
     * 3. authenticationToken을 이용하여 UserDetailsService에서 인증 정보를 받아온다.
     *          -> (내부적으로 해주기에 코드상에 보이진 않음)
     * @Return : 인증이 완료된(권한부여된) Authentication
     */
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){

        ObjectMapper objectMapper = new ObjectMapper();
        LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);

        //아직 인증되지 않은 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken
            = new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }


    /**
     * attempt 성공시 진입하게 될 메서드.
     * 1. 인증된 Authentication의 field에서 Member정보를 가져온다.
     * 2. 가져온 member를 이용하여 액세스토큰, 리프레시 토큰을 생성한다.
     * 3. 리프레시 토큰을 데이터베이스에 저장한다. - 추후 로그아웃을 구현하기 위해
     *      -> 관련 레포지터리 접근은 jwtTokenizer에 구현해둠
     * 4. 리프레시 토큰과 memberId를 쿠키에 담는다. (쿠키 사용을 위해 해봄)
     * 5. 액세스 토큰은 헤더에 담는다.
     * method.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        Authentication authResult) throws ServletException, IOException {

        //인증에 성공할 경우에 호출됨
        MemberDetails member = (MemberDetails) authResult.getPrincipal();

        String accessToken = delegateAccessToken(member);
        String refreshToken = delegateRefreshToken(member);

        //redis에 refreshToken 저장
        redisRepository.saveRefresh(refreshToken,"True");
        //jwtTokenizer.saveRefreshToken(refreshToken,member.getEmail(),member.getMemberId());

//        //쿠키에 담기 위해 encoding
//        String encodedRefreshToken = URLEncoder.encode(refreshToken, "UTF-8");
//        Cookie RefreshCookie = new Cookie("RefreshToken", encodedRefreshToken);
//        // "/"하위 모든 경로에 cookie 전송
//        RefreshCookie.setPath("/");
//        RefreshCookie.setHttpOnly(false);
//
//        Cookie MemberCookie = new Cookie("MemberId", String.valueOf(member.getMemberId()));
//        MemberCookie.setPath("/");
//        MemberCookie.setHttpOnly(false);
//
//        response.addCookie(RefreshCookie);
//        response.addCookie(MemberCookie);
//
        response.setHeader("memberId", String.valueOf(member.getMemberId()));
        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Refresh", refreshToken);

        //failureHandler는 실패했을 때 자동으로 실행된다
        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }

    /**
     * AccessToken 생성 로직
     * @Param 인증된 Authentication의 principal field에서 찾아온 member정보
     * @Return AccessToken
     */
    private String delegateAccessToken(MemberDetails member) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", member.getMemberId());
        claims.put("username", member.getEmail());
        claims.put("roles", member.getRoles());

        String subject = member.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    /**
     * RefreshToken 생성 로직
     * @Param 인증된 Authentication의 principal field에서 찾아온 member정보
     * @Return RefreshToken
     */
    private String delegateRefreshToken(MemberDetails member) {
        String subject = member.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }

}
