package Be_30.Project.answer.dto;

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
    public static class Post {
        @NotBlank(message = "글을 입력해주세요.")
        private String content;
        @NotBlank
        private int vote; // 별도의 Entity로 빼는 것이 좋지 않나? voteCount? notBlank가 옳은가? 0점은?
        @NotNull
        private boolean accepted = false;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch {
        @NotBlank(message = "글을 입력해주세요.")
        private String content;
        @NotBlank
        private int vote;
        @NotNull
        private boolean accepted;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private Long answerId;
        private String content;
        private int vote;
        private boolean accepted;
        private LocalDateTime createdAt = LocalDateTime.now();
        private LocalDateTime modifiedAt = LocalDateTime.now();
    }

}
