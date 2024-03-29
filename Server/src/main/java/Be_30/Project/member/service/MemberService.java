package Be_30.Project.member.service;

import Be_30.Project.auth.jwt.JwtTokenizer;
import Be_30.Project.auth.jwt.refreshtoken.repository.RedisRepository;
import Be_30.Project.auth.oauth.CustomOauthMember;
import Be_30.Project.auth.oauth.OauthMemberRepository;
import Be_30.Project.auth.userdetails.MemberDetails;
import Be_30.Project.auth.utils.CustomAuthorityUtils;
import Be_30.Project.exception.BusinessLogicException;
import Be_30.Project.exception.ExceptionCode;
import Be_30.Project.file.service.FileService;
import Be_30.Project.member.entity.Member;
import Be_30.Project.member.entity.Member.MemberStatus;
import Be_30.Project.member.repository.MemberRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private CustomAuthorityUtils authorityUtils;
    private final JwtTokenizer jwtTokenizer;
    private final FileService fileService;
    private final OauthMemberRepository oauthMemberRepository;
    private final RedisRepository redisRepository;

    public MemberService(@Lazy MemberRepository memberRepository, @Lazy PasswordEncoder passwordEncoder,
        @Lazy CustomAuthorityUtils authorityUtils, @Lazy JwtTokenizer jwtTokenizer,
        @Lazy FileService fileService, @Lazy OauthMemberRepository oauthMemberRepository,@Lazy RedisRepository redisRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityUtils = authorityUtils;
        this.jwtTokenizer = jwtTokenizer;
        this.fileService = fileService;
        this.oauthMemberRepository = oauthMemberRepository;
        this.redisRepository = redisRepository;
    }

    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());

        if (member.getPassword().equals(member.getConfirmedPassword())) {
            //pw 암호화
            String encryptedPassword = passwordEncoder.encode(member.getPassword());
            member.setPassword(encryptedPassword);

            String encryptedConfirmedPassword = passwordEncoder.encode(member.getConfirmedPassword());
            member.setConfirmedPassword(encryptedConfirmedPassword);

            List<String> roles = authorityUtils.createRoles(member.getEmail());
            member.setRoles(roles);

            // 기본 프로필 사진 지정
            member.setProfileImageSrc(fileService.getRandomDefaultImageSrc());

            return memberRepository.save(member);
        } else {
            throw new BusinessLogicException(ExceptionCode.PASSWORD_NOT_CONFIRMED);
        }
    }

    public Member createSocialMember(Member member) {
        verifyExistsEmail(member.getEmail());

        if(member.getOauthPlatform() != null) {
            // 소셜 회원 가입
            List<String> roles = authorityUtils.createRoles(member.getEmail());
            member.setRoles(roles);

            // 소셜 회원가입 테이블에 정보가 있어야만 진행 가능
            CustomOauthMember oauthMember = oauthMemberRepository.findByEmail(member.getEmail())
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.OAUTH_MEMBER_NOT_FOUND));
            member.setProfileImageSrc(oauthMember.getProfileImageSrc());
            member.setPassword("{noop}1234"); // 현 상황에서 어쩔 수 없음
            member.setConfirmedPassword("{noop}1234");

            oauthMemberRepository.delete(oauthMember); // 회원가입과 동시에 임시 테이블 데이터는 삭제
            return memberRepository.save(member);
        } else {
            throw new BusinessLogicException(ExceptionCode.OAUTH_MEMBER_NOT_FOUND);
        }
    }


    public Member updateMember(Member member, String email) {
        Member findMember = findVerifiedMember(member.getMemberId(), email);

//        if(!findMember.getEmail().equals(email)){
//            throw new BusinessLogicException(ExceptionCode.NOT_AUTHORIZED);
//        }

        Optional.ofNullable(member.getNickName())
                .ifPresent(name -> findMember.setNickName(name));
        Optional.ofNullable(member.getMemberStatus())
                .ifPresent(memberStatus -> findMember.setMemberStatus(memberStatus));

        return memberRepository.save(findMember);
    }

    public Member findMember(long memberId, String email) {
        if (!email.isEmpty()) {
            return findVerifiedMember(memberId, email);
        }
            return memberRepository.findByMemberIdAndMemberStatus(memberId, MemberStatus.MEMBER_ACTIVE).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
//        Optional<Member> findMember = Optional.of(
//            memberRepository.findById(memberId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND)));
//
//        if(findMember.get().equals(MemberStatus.MEMBER_QUIT)){
//            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
//        }
//
//        return findMember.get();
    }


    public Page<Member> findMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page, size,
            Sort.by("memberId").descending()));
    }



    public void deleteMember(long memberId, String email) {
        Member findMember = findVerifiedMember(memberId,email);

        findMember.setMemberStatus(MemberStatus.MEMBER_QUIT);
        findMember.setEmail("");
        findMember.setNickName("");


    }

    public Member findVerifiedMember(long memberId, String email) {
        Optional<Member> optionalMember =
                memberRepository.findById(memberId);
        Member findMember =
                optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        if (findMember.getEmail().equals(email)) {
            return findMember;
        } else {
            throw new BusinessLogicException(ExceptionCode.NOT_AUTHORIZED);
        }
    }

    private void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
    }




    public void logout(HttpServletRequest request,Long memberId){
        String accessToken = request.getHeader("Authorization").replace("Bearer","");

        int minutes = jwtTokenizer.getAccessTokenExpirationMinutes();
        long now  = new Date().getTime();

        int expiration = (int)(jwtTokenizer.getTokenExpiration(minutes).getTime() - now);

        redisRepository.setBlackList(accessToken, "True",
            expiration);

        redisRepository.expireRefreshToken(memberId.toString());
    }
    public String reissue(MemberDetails memberDetails, String refresh){
        if(redisRepository.hasRefresh(refresh)) {
            Map<String, Object> claims = new HashMap<>();

            claims.put("memberId", memberDetails.getMemberId());
            claims.put("username", memberDetails.getEmail());
            claims.put("roles", memberDetails.getRoles());
            String subject = memberDetails.getEmail();

            Date expiration = jwtTokenizer.getTokenExpiration(
                jwtTokenizer.getAccessTokenExpirationMinutes());

            String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(
                jwtTokenizer.getSecretKey());

            String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration,
                base64EncodedSecretKey);

            return accessToken;
        }else{
            throw new BusinessLogicException(ExceptionCode.NOT_AUTHORIZED);
        }
    }
}
