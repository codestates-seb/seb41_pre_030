package Be_30.Project.vote.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class QuestionVoteResponseDto {
    private long questionVoteId;
    private int votes;
}
