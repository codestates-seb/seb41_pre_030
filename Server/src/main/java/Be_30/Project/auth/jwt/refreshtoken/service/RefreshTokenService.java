package Be_30.Project.auth.jwt.refreshtoken.service;

import Be_30.Project.auth.jwt.JwtTokenizer;
import Be_30.Project.auth.jwt.refreshtoken.entity.RefreshToken;
import Be_30.Project.auth.jwt.refreshtoken.repository.RefreshTokenRepository;
import Be_30.Project.exception.BusinessLogicException;
import Be_30.Project.exception.ExceptionCode;
import Be_30.Project.member.entity.Member;
import Be_30.Project.member.repository.MemberRepository;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenizer jwtTokenizer;


    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository,
        MemberRepository memberRepository, JwtTokenizer jwtTokenizer) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.memberRepository = memberRepository;
        this.jwtTokenizer = jwtTokenizer;
    }


    public void logoutMember(String refreshToken) {
        RefreshToken findToken = checkExistToken(refreshToken);
        refreshTokenRepository.delete(findToken);
    }

    private RefreshToken checkExistToken(String refreshToken) {
        return refreshTokenRepository
            .findRefreshTokenByTokenValue(refreshToken)
            .orElseThrow(() -> new BusinessLogicException(ExceptionCode.TOKEN_NOT_FOUND));
    }


}
