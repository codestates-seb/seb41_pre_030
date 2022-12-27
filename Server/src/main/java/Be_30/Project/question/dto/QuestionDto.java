package Be_30.Project.question.dto;

import Be_30.Project.answer.dto.AnswerDto;
import Be_30.Project.member.dto.MemberDto;
import Be_30.Project.question.entity.Question;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

public class QuestionDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        @NotBlank(message = "제목을 입력해주세요.")
        private String subject;

        @NotBlank(message = "본문을 입력해주세요.")
        private String content;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch {
        @Setter private long questionId;

        @NotBlank(message = "제목을 입력해주세요.")
        private String subject;

        @NotBlank(message = "본문을 입력해주세요.")
        private String content;
    }

    @Getter
    @Builder
    public static class Response {
        private long questionId;
        private String subject;
        private String content;
        private int votes;
        private int views;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private MemberDto.Response member;
        private List<AnswerDto.Response> answers;
    }

    @Getter
    @Builder
    public static class ResponseOnlyQuestion {
        private long questionId;
        private String subject;
        private String content;
        private int votes;
        private int views;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private int answerCount; // 답변 개수
    }


    @Getter
    @Builder
    public static class ResponseWithoutAnswers {
        private long questionId;
        private String subject;
        private String content;
        private int votes;
        private int views;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private MemberDto.Response member;
        private int answerCount; // 답변 개수
    }
}
