package Be_30.Project.member.controller;

import Be_30.Project.member.dto.MemberDto;
import Be_30.Project.member.entity.Member.MemberStatus;
import Be_30.Project.member.mapper.MemberMapper;
import Be_30.Project.member.repository.MemberRepository;
import Be_30.Project.member.service.MemberService;
import java.net.URI;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity postMember(){
        return ResponseEntity.created(URI.create("/members/1")).build();
    }

    @PatchMapping("/{user-id}")
    public ResponseEntity patchMember(){
        MemberDto.Response response =
            new MemberDto.Response(1,"heebum@gmail.com",
                "희범",MemberStatus.MEMBER_ACTIVE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{user-id}")
    public ResponseEntity getMember(){
        MemberDto.Response response =
            new MemberDto.Response(1,"heebum@gmail.com",
                "희범",MemberStatus.MEMBER_ACTIVE);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{user-id}")
    public ResponseEntity deleteMember(){
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity getMembers(){
        MemberDto.Response response1 =
            new MemberDto.Response(1,"heebum1@gmail.com",
                "희범1",MemberStatus.MEMBER_ACTIVE);

        MemberDto.Response response2 =
            new MemberDto.Response(2,"heebum2@gmail.com",
                "희범2",MemberStatus.MEMBER_ACTIVE);
        return ResponseEntity.ok(Arrays.asList(response1, response2));
    }
}
