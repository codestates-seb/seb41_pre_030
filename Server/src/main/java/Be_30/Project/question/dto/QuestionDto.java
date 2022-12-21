package Be_30.Project.question.dto;

import lombok.*;

import java.time.LocalDateTime;

public class QuestionDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        private String subject;
        private String content;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch {
        @Setter private long questionId;
        private String subject;
        private String content;
    }

    @Getter
    @Builder
    public static class Response {
        private long questionId;
        private String subject;
        private String content;
        private int vote;
        private int view;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}
