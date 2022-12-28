package Be_30.Project.member.service;

import Be_30.Project.auth.jwt.JwtTokenizer;
import Be_30.Project.auth.utils.CustomAuthorityUtils;
import Be_30.Project.exception.BusinessLogicException;
import Be_30.Project.exception.ExceptionCode;
import Be_30.Project.member.entity.Member;
import Be_30.Project.member.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
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

    public MemberService(@Lazy MemberRepository memberRepository, @Lazy PasswordEncoder passwordEncoder,
        @Lazy CustomAuthorityUtils authorityUtils, @Lazy JwtTokenizer jwtTokenizer) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityUtils = authorityUtils;
        this. jwtTokenizer = jwtTokenizer;
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

        return findVerifiedMember(memberId,email);
    }


    public Page<Member> findMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page, size,
            Sort.by("memberId").descending()));
    }



    public void deleteMember(long memberId, String email) {
        Member findMember = findVerifiedMember(memberId,email);

        memberRepository.delete(findMember);
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
}
