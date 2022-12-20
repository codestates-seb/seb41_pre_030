package Be_30.Project.member.controller;

import Be_30.Project.member.dto.MemberDto;
import Be_30.Project.member.entity.Member.MemberStatus;
import Be_30.Project.member.mapper.MemberMapper;
import Be_30.Project.member.repository.MemberRepository;
import java.net.URI;
import java.util.Arrays;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {

    MemberRepository memberRepository;
    MemberMapper memberMapper;

    public MemberController(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    @PostMapping
    public ResponseEntity postMember(){
        return ResponseEntity.created(URI.create("/members/1")).build();
    }

    @PatchMapping("/{user-id}")
    public ResponseEntity patchMember(){
        MemberDto.Response response =
            new MemberDto.Response(1,"heebum@gmail.com",
                "희범", "1234", MemberStatus.MEMBER_ACTIVE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{user-id}")
    public ResponseEntity getMember(){
        MemberDto.Response response =
            new MemberDto.Response(1,"heebum@gmail.com",
                "희범", "1234", MemberStatus.MEMBER_ACTIVE);
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
                "희범1", "1111", MemberStatus.MEMBER_ACTIVE);

        MemberDto.Response response2 =
            new MemberDto.Response(2,"heebum2@gmail.com",
                "희범2", "2222", MemberStatus.MEMBER_ACTIVE);
        return ResponseEntity.ok(Arrays.asList(response1, response2));
    }
}
