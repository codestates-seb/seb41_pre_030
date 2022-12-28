package Be_30.Project.answer.dto;

import Be_30.Project.member.dto.MemberDto;
import Be_30.Project.member.entity.Member;
import Be_30.Project.question.dto.QuestionDto;
import Be_30.Project.question.entity.Question;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class AnswerDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {

        @NotBlank(message = "글을 입력해주세요.")
        private String content;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch {
        private long answerId;
        @NotBlank(message = "글을 입력해주세요.")
        private String content;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private Long answerId;
        private String content;
        private boolean adopt;
        private int votes;
        private MemberDto.ResponseOnlyMember member;
        private QuestionDto.ResponseWithoutAnswers question;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}
