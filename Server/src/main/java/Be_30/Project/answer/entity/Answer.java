package Be_30.Project.answer.entity;


import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

public class Answer {
    private Long answerId;
    private String content;
    private int vote = 0; // 추천수 음수~양수 허용 ok
    private boolean accepted;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime modifiedAt = LocalDateTime.now();
}
