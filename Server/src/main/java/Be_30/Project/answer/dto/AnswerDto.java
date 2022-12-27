package Be_30.Project.answer.dto;

import Be_30.Project.member.dto.MemberDto;
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
        private MemberDto.Response member;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ResponseOnlyAnswer {
        private Long answerId;
        private String content;
        private boolean adopt;
        private int votes;
    }

}
