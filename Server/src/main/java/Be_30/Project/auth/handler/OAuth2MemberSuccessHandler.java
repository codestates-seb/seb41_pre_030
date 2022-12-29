package Be_30.Project.auth.handler;

import Be_30.Project.auth.jwt.JwtTokenizer;
import Be_30.Project.auth.utils.CustomAuthorityUtils;
import Be_30.Project.member.entity.Member;
import Be_30.Project.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
public class OAuth2MemberSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = String.valueOf(attributes.get("email")); // Resource Owner Email 주소
        List<String> authorities = authorityUtils.createRoles(email); // 권한 정보 생성

        String requestURI = request.getRequestURI();
        // Attribute 변수명이 살짝씩 다르므로 분리
        String profileImageSrc;
        if (requestURI.contains("google")) { // Google OAuth2 로그인
            profileImageSrc = String.valueOf(attributes.get("picture"));
        } else { // Github OAuth2 로그인
            profileImageSrc = String.valueOf(attributes.get("avatar_url"));
        }

        // ** 회원 가입이 안되어있으면 회원 등록도 같이 진행해야함 **
        Member savedMember = memberRepository.findByEmail(email)
                .orElseGet(() -> {
                    Member member = new Member();
                    // * oauth2의 경우 비밀번호가 없음 = 어떡하지? => Member 엔티티에 OAuth 유저인지 일반 유저인지 구분?*
                    member.setPassword("{noop}1234");
                    member.setConfirmedPassword("{noop}1234");
                    member.setEmail(email);
                    member.setRoles(authorities);
                    member.setNickName(String.valueOf(attributes.get("name")));
                    member.setProfileImageSrc(profileImageSrc);

                    return memberRepository.save(member);
                });

        String accessToken = delegateAccessToken(savedMember);
        String refreshToken = delegateRefreshToken(email);

        jwtTokenizer.saveRefreshToken(refreshToken, savedMember.getEmail(), savedMember.getMemberId());

        response.setHeader("memberId", String.valueOf(savedMember.getMemberId()));
        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Refresh", refreshToken);
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
}
