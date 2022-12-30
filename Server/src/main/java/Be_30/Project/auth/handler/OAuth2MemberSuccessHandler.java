package Be_30.Project.auth.handler;

import Be_30.Project.auth.jwt.JwtTokenizer;
import Be_30.Project.auth.utils.CustomAuthorityUtils;
import Be_30.Project.dto.ErrorResponse;
import Be_30.Project.exception.BusinessLogicException;
import Be_30.Project.exception.ExceptionCode;
import Be_30.Project.member.entity.Member;
import Be_30.Project.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
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

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

        // Attribute 변수명이 살짝씩 다르므로 분리
        String profileImageSrc;
        Member.OauthPlatform oauthPlatform;
        if (requestURI.contains("google")) {
            oauthPlatform = Member.OauthPlatform.GOOGLE;
            profileImageSrc = String.valueOf(attributes.get("picture"));
        } else {
            oauthPlatform = Member.OauthPlatform.GITHUB;
            profileImageSrc = String.valueOf(attributes.get("avatar_url"));
        }

        // 회원가입이 안되어있으면 소셜 유저 정보로 회원가입을 진행

        Optional<Member> optionalMember = memberRepository.findByEmailAndOauthPlatform(email, oauthPlatform);
        Member savedMember;
        if(optionalMember.isEmpty()) {

            // 이메일 계정이 이미 있으면 중복 가입 X
            if (memberRepository.findByEmail(email).isPresent()) {
                queryParams.add("error",
                        URLEncoder.encode(ExceptionCode.MEMBER_EXISTS.getMessage(), StandardCharsets.UTF_8));
                String uri = createURI(queryParams).toString();
                getRedirectStrategy().sendRedirect(request, response, uri);
                return;
            }

            Member member = new Member();
            member.setPassword("{noop}1234");
            member.setConfirmedPassword("{noop}1234");
            member.setOauthPlatform(oauthPlatform);
            member.setEmail(email);
            member.setRoles(authorities);
            member.setNickName(String.valueOf(attributes.get("name")));
            member.setProfileImageSrc(profileImageSrc);

            savedMember = memberRepository.save(member);
        } else {
            savedMember = optionalMember.get();
        }

        savedMember.setLastLogin(LocalDateTime.now());
        memberRepository.save(savedMember);

        String accessToken = delegateAccessToken(savedMember);
        String refreshToken = delegateRefreshToken(email);
        jwtTokenizer.saveRefreshToken(refreshToken, savedMember.getEmail(), savedMember.getMemberId());

        queryParams.add("access_token", String.format("Bearer %s", accessToken));
        queryParams.add("refresh_token", refreshToken);

        String uri = createURI(queryParams).toString();
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

    private URI createURI(MultiValueMap<String, String> queryParams) {
        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("13.125.30.88")
                .port(8080)
                .path("/")
                .queryParams(queryParams)
                .build()
                .toUri();
    }
}
