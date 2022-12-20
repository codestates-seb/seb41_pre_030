package Be_30.Project.question.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class QuestionResponseDto {
    private long questionId;
    // private UserResponseDto userResponseDto; // 질문 작성자 정보
    private String subject;
    private String content;
    private int vote;
    private int view;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
