package Be_30.Project.auth.filter;

import Be_30.Project.auth.jwt.JwtTokenizer;
import Be_30.Project.auth.jwt.refreshtoken.repository.RedisRepository;
import Be_30.Project.auth.userdetails.MemberDetails;
import Be_30.Project.auth.utils.CustomAuthorityUtils;
import Be_30.Project.exception.BusinessLogicException;
import Be_30.Project.exception.ExceptionCode;
import Be_30.Project.member.entity.Member;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {

    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final RedisRepository redisRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //검증 실패했을 때 생기는 SignatureException 과 JWT가 만료될 경우에 생기는 ExpiredJwtException에대한 처리
        try {
            //refreshtoken repository에서 토큰 확인
            //redis -> refreshToken을 RefreshRepository에서 확인하는 절차 삭제
//            String refreshToken = request.getHeader("Refresh");
//            jwtTokenizer.verifiedRefreshToken(refreshToken);

            Map<String, Object> claims = verifyJws(request);
            setAuthenticationToContext(claims); //Authentication 객체를 securityContext에 저장하기 위한 메서드
        } catch (SignatureException se) {
            request.setAttribute("exception", se);
            // 서명 검증에 실패했을 때 SecurityContext에 인증정보인 Authentication 객체가 저장되지 않는다.
        } catch (ExpiredJwtException ee) {
            request.setAttribute("exception", ee);
        }
            // 만료되었을 때 SecurityContext에 인증정보인 Authentication 객체가 저장되지 않는다.
        catch (BusinessLogicException be) {
            be.printStackTrace();
            request.setAttribute("exception", be);
        }
        catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);//다음 security filter를 호출한다.
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        //jwt가 Authorization header에 포함되지 않았다면 filter 실행하지 않는다.
        String authorization = request.getHeader("Authorization");

        return authorization == null || !authorization.startsWith("Bearer");
    }

    private void setAuthenticationToContext(Map<String, Object> claims) {
        // JWT Token 내부의 값(claims)을 이용해 MemberDetails 생성
        // -> Security Context에 MemberDetails 객체 저장
        // -> 컨트롤러에서 @AuthenticatonPrincipal MemberDetails 로 조회 가능
        long memberId = Long.parseLong(String.valueOf(claims.get("memberId")));
        String username = (String) claims.get("username");
        List<String> roles = (List<String>) claims.get("roles");
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities(roles);

        Member member = new Member();
        member.setMemberId(memberId);
        member.setEmail(username);
        member.setRoles(roles);
        MemberDetails memberDetails = new MemberDetails(authorityUtils, member);

        Authentication authentication = new UsernamePasswordAuthenticationToken(memberDetails, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Map<String, Object> verifyJws(HttpServletRequest request) {
        String jws = request.getHeader("Authorization").replace("Bearer ", "");
        if(redisRepository.hasAccess(jws)){
            throw new BusinessLogicException(ExceptionCode.NOT_AUTHORIZED);
        }
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        return jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();

    }
}
