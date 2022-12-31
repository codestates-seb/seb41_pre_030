package Be_30.Project.auth.oauth;

import Be_30.Project.auth.jwt.JwtTokenizer;
import Be_30.Project.auth.utils.CustomAuthorityUtils;
import Be_30.Project.member.entity.Member;
import Be_30.Project.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

@Transactional
@RequiredArgsConstructor
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${custom.frontend.host}")
    private String frontendHost;

    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final MemberRepository memberRepository;
    private final OauthMemberRepository oauthMemberRepository;

    @Override // 소셜 로그인에 성공해 해당 계정 정보를 가져온 이후 과정
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 1. Resource Owner의 소셜 계정 정보를 파싱
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal(); // Resource Owner 소셜 계정 정부
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = String.valueOf(attributes.get("email"));

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

        // 2. 회원가입이 되어있지 않은 상태라면 임시 테이블에 해당 정보를 넣어두고 회원가입 경로로 전달
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isEmpty()) {
            // 플랫폼 구분
            String requestURI = request.getRequestURI();
            CustomOauthMember oauthMember;
            if(requestURI.contains("google")) {
                oauthMember = new CustomOauthMember(oAuth2User, "google");
            } else {
                oauthMember = new CustomOauthMember(oAuth2User, "github");
            }
            oauthMemberRepository.save(oauthMember);
            queryParams.add("nickName", URLEncoder.encode(String.valueOf(oauthMember.getNickname()), StandardCharsets.UTF_8));
            queryParams.add("email", email);
            queryParams.add("platform", oauthMember.getOauthPlatform().name());
            getRedirectStrategy().sendRedirect(request, response, createURI(queryParams, "/login/oauth2/signup").toString());
            return;
        }

        // 3. 회원가입이 되어있는 유저라면 서버의 JWT 토큰을 만들어 전달
        Member savedMember = optionalMember.get();

        savedMember.setLastLogin(LocalDateTime.now());
        memberRepository.save(savedMember);

        String accessToken = delegateAccessToken(savedMember);
        String refreshToken = delegateRefreshToken(email);
        jwtTokenizer.saveRefreshToken(refreshToken, savedMember.getEmail(), savedMember.getMemberId());

        queryParams.add("access_token", String.format("Bearer %s", accessToken));
        queryParams.add("refresh_token", refreshToken);

        String uri = createURI(queryParams, "/").toString();
        getRedirectStrategy().sendRedirect(request, response, uri);
    }

    private String delegateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", member.getMemberId());
        claims.put("username", member.getEmail());
        claims.put("roles", authorityUtils.createRoles("email"));

        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        return jwtTokenizer.generateAccessToken(claims, member.getEmail(), expiration, base64EncodedSecretKey);
    }

    private String delegateRefreshToken(String username) {
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        return jwtTokenizer.generateRefreshToken(username, expiration, base64EncodedSecretKey);
    }

    private URI createURI(MultiValueMap<String, String> queryParams, String path) {
        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host(frontendHost)
                .port(8080)
                .path(path)
                .queryParams(queryParams)
                .build()
                .toUri();
    }
}
