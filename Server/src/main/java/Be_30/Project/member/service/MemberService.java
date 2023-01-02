package Be_30.Project.member.service;

import Be_30.Project.auth.jwt.JwtTokenizer;
import Be_30.Project.auth.jwt.refreshtoken.repository.RedisRepository;
import Be_30.Project.auth.utils.CustomAuthorityUtils;
import Be_30.Project.exception.BusinessLogicException;
import Be_30.Project.exception.ExceptionCode;
import Be_30.Project.member.entity.Member;
import Be_30.Project.member.entity.Member.MemberStatus;
import Be_30.Project.member.repository.MemberRepository;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private CustomAuthorityUtils authorityUtils;
    private final RedisRepository redisRepository;

    private final JwtTokenizer jwtTokenizer;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder,
        CustomAuthorityUtils authorityUtils, RedisRepository redisRepository, JwtTokenizer jwtTokenizer) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityUtils = authorityUtils;
        this.redisRepository = redisRepository;
        this.jwtTokenizer = jwtTokenizer;
    }

    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());

        if(member.getPassword().equals(member.getConfirmedPassword())){
            //pw 암호화
            String encryptedPassword = passwordEncoder.encode(member.getPassword());
            member.setPassword(encryptedPassword);

            String encryptedConfirmedPassword = passwordEncoder.encode(member.getConfirmedPassword());
            member.setConfirmedPassword(encryptedConfirmedPassword);

            List<String> roles = authorityUtils.createRoles(member.getEmail());
            member.setRoles(roles);

            return memberRepository.save(member);
        }else{
            throw new BusinessLogicException(ExceptionCode.PASSWORD_NOT_CONFIRMED);
        }
    }


    public Member updateMember(Member member, String email) {
        Member findMember = findVerifiedMember(member.getMemberId(),email);

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
        if(!email.isEmpty()){
            return findVerifiedMember(memberId,email);
        }
            return memberRepository.findByMemberIdAndMemberStatus(memberId,MemberStatus.MEMBER_ACTIVE).orElseThrow(
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
        return memberRepository.findAllByMemberStatus(PageRequest.of(page, size,
            Sort.by("memberId").descending()),MemberStatus.MEMBER_ACTIVE);
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
        if(findMember.getEmail().equals(email)){
            return findMember;
        }else {
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
}
