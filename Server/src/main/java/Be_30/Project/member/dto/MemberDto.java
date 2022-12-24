package Be_30.Project.member.dto;

import Be_30.Project.member.entity.Member.MemberStatus;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class MemberDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post{
        @NotBlank
        @Email
        private String email;

        @NotBlank(message = "이름을 정해주세요")
        private String nickName;

        @NotBlank(message = "비밀번호를 정해주세요")
        private String password;

        @NotBlank(message = "비밀번호를 한번 더 확인해주세요")
        private String confirmedPassword;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch{

        private long memberId;
        @NotBlank
        @Email
        private String email;

        @NotBlank(message = "이름을 정해주세요")
        private String nickName;

        private MemberStatus memberStatus;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response{
        private long memberId;

        @NotBlank
        @Email
        private String email;

        @NotBlank(message = "이름을 정해주세요")
        private String nickName;

        private MemberStatus memberStatus;
    }

}
