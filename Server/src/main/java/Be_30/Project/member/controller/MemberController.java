package Be_30.Project.member.controller;

import Be_30.Project.auth.userdetails.MemberDetails;
import Be_30.Project.dto.MultiResponseDto;
import Be_30.Project.dto.SingleResponseDto;
import Be_30.Project.member.dto.MemberDto;
import Be_30.Project.member.entity.Member;
import Be_30.Project.member.mapper.MemberMapper;
import Be_30.Project.member.service.MemberService;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@Validated
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper mapper;

    @PostMapping("/signup")
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post memberDto) {
        Member member = mapper.MemberPostDtoToMember(memberDto);

        Member createdMember = memberService.createMember(member);
        MemberDto.Response response = mapper.MemberToMemberResponseDto(createdMember);

        return new ResponseEntity<>(
                new SingleResponseDto<>(response),
                HttpStatus.CREATED);
    }

    @PostMapping("/signup/oauth") // 소셜 로그인 회원가입
    public ResponseEntity signup(@Valid @RequestBody MemberDto.OauthPost memberDto) {
        Member member = mapper.OauthMemberDtoToMember(memberDto);
        member.setOauthPlatform(Member.OauthPlatform.valueOf(memberDto.getPlatform()));

        Member savedMember = memberService.createSocialMember(member);
        MemberDto.Response response = mapper.MemberToMemberResponseDto(savedMember);
        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.CREATED);
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@Valid @PathVariable("member-id") int id,
                                      @RequestBody MemberDto.Patch memberDto,
                                      @AuthenticationPrincipal MemberDetails memberDetails) {
        Member member = mapper.MemberPatchDtoToMember(memberDto);
        String email = memberDetails.getEmail();

        Member updatedMember = memberService.updateMember(member, email);

        MemberDto.Response response = mapper.MemberToMemberResponseDto(updatedMember);

        return new ResponseEntity<>(
                new SingleResponseDto<>(response),
                HttpStatus.CREATED);

    }


    @GetMapping("/mypage/{member-id}")
    public ResponseEntity getMember(@Positive @PathVariable("member-id") int id
            , @AuthenticationPrincipal MemberDetails memberDetails) {

        String email = memberDetails.getEmail();

        Member member = memberService.findMember(id, email);

        MemberDto.Response response = mapper.MemberToMemberResponseDto(member);

        return new ResponseEntity<>(
                new SingleResponseDto<>(response),
                HttpStatus.OK);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@Positive @PathVariable("member-id") int id
        ) {

        Member member = memberService.findMember(id,"");

        MemberDto.Response response = mapper.MemberToMemberResponseDto(member);

        return new ResponseEntity<>(
            new SingleResponseDto<>(response),
            HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@Positive @PathVariable("member-id") int id, @AuthenticationPrincipal MemberDetails memberDetails) {
        String email = memberDetails.getEmail();

        memberService.deleteMember(id, email);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity getMembers(@Positive @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                     @Positive @RequestParam(name = "size", required = false, defaultValue = "15") int size) {
        Page<Member> pageMembers = memberService.findMembers(page - 1, size);
        List<Member> members = pageMembers.getContent();

        MultiResponseDto multiResponseDto = new MultiResponseDto(mapper.MembersToMemberResponseDtos(members)
                , pageMembers);

        return new ResponseEntity<>(multiResponseDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity loginMember(@AuthenticationPrincipal MemberDetails memberDetails){
        Member member = new Member();
        member.setMemberId(memberDetails.getMemberId());
        member.setEmail(memberDetails.getEmail());
        member.setRoles(memberDetails.getRoles());

        MemberDto.Response response = mapper.MemberToMemberResponseDto(member);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

//    private String getEmailByRequest(HttpServletRequest request) {
//
//        String jws = request.getHeader("Authorization").replace("Bearer","");
//        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
//        System.out.println(jwtTokenizer.getClaims(jws,base64EncodedSecretKey).getBody());
//        System.out.println(jwtTokenizer.getClaims(jws,base64EncodedSecretKey).getBody().getSubject());
//        return jwtTokenizer.getClaims(jws,base64EncodedSecretKey).getBody().getSubject();
//
//    }
}
