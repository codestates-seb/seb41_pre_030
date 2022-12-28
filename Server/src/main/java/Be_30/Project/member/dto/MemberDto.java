package Be_30.Project.member.dto;

import Be_30.Project.answer.dto.AnswerDto;
import Be_30.Project.member.entity.Member.MemberStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import Be_30.Project.question.dto.QuestionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class MemberDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
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
    public static class Patch {

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
    public static class Response {
        private long memberId;

        @NotBlank
        @Email
        private String email;

        @NotBlank(message = "이름을 정해주세요")
        private String nickName;

        private MemberStatus memberStatus;

        private String profileImageSrc;

        private List<QuestionDto.ResponseOnlyQuestion> questions;

        private List<AnswerDto.Response> answers;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ResponseOnlyMember{
        private long memberId;

        private String email;

        private String nickName;

        private MemberStatus memberStatus;

        private String profileImageSrc;
    }
}
