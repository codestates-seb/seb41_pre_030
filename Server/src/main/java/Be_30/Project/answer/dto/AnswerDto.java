package Be_30.Project.answer.dto;

import java.time.LocalDateTime;

public class AnswerDto {
    public static class Post {
        private String content;
        private int vote;  // 추천수 post에 넣는다?? 
        private boolean accepted;
    }
    public static class Patch {
        private String content;
        private int vote;
        private boolean accepted;
    }
    public static class Response {
        private Long answerId;
        private String content;
        private int vote;
        private boolean accepted;
        private LocalDateTime createAt;
        private LocalDateTime modifiedAt;

        public Response(Long answerId, String content, int vote, boolean accepted) {
            this.answerId = answerId;
            this.content = content;
            this.vote = vote;
            this.accepted = accepted;
        }
    }

}
