package Be_30.Project.answer.entity;


import java.time.LocalDateTime;

public class Answer {
    private Long answerId;
    private String content;
    private int vote; // 추천수 음수~양수 허용 ok
    private boolean accepted;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
}
