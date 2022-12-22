package Be_30.Project.member.controller;

import Be_30.Project.dto.MultiResponseDto;
import Be_30.Project.dto.SingleResponseDto;
import Be_30.Project.member.dto.MemberDto;
import Be_30.Project.member.entity.Member;
import Be_30.Project.member.entity.Member.MemberStatus;
import Be_30.Project.member.mapper.MemberMapper;
import Be_30.Project.member.repository.MemberRepository;
import Be_30.Project.member.service.MemberService;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@Validated
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper mapper;

    public MemberController(MemberService memberService, MemberMapper mapper) {
        this.memberService = memberService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post memberDto){
        Member member = mapper.MemberPostDtoToMember(memberDto);

        Member createdMember = memberService.createMember(member);
        MemberDto.Response response = mapper.MemberToMemberResponseDto(createdMember);

        return new ResponseEntity<>(
            new SingleResponseDto<>(response),
            HttpStatus.CREATED);

    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@Valid @PathVariable("member-id") int id, @RequestBody MemberDto.Patch memberDto){
        memberDto.setMemberId(id);

        Member member = memberService.updateMember(mapper.MemberPatchDtoToMember(memberDto));
        MemberDto.Response response = mapper.MemberToMemberResponseDto(member);

        return new ResponseEntity<>(
            new SingleResponseDto<>(response),
            HttpStatus.OK);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@Positive @PathVariable("member-id") int id){
        Member member = memberService.findMember(id);

        MemberDto.Response response = mapper.MemberToMemberResponseDto(member);

        return new ResponseEntity<>(
            new SingleResponseDto<>(response),
            HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@Positive @PathVariable("member-id") int id){
        memberService.deleteMember(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity getMembers(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size){
        Page<Member> pageMembers = memberService.findMembers(page-1, size);
        List<Member> members = pageMembers.getContent();

        MultiResponseDto multiResponseDto = new MultiResponseDto(mapper.MembersToMemberResponseDtos(members)
        , pageMembers);

        return new ResponseEntity<>(multiResponseDto,HttpStatus.OK);
    }
}
