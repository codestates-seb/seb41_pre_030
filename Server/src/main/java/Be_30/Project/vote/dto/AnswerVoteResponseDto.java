package Be_30.Project.vote.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AnswerVoteResponseDto {
    private long answerVoteId;
    private int votes;
}