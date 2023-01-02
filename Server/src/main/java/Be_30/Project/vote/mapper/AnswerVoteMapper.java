package Be_30.Project.vote.mapper;

import Be_30.Project.vote.dto.AnswerVoteResponseDto;
import Be_30.Project.vote.entity.AnswerVote;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerVoteMapper {
    default AnswerVoteResponseDto AnswerVoteToAnswerVoteResponseDto (AnswerVote answerVote) {
        if(answerVote == null) {
            return null;
        }
        AnswerVoteResponseDto response = AnswerVoteResponseDto.builder()
            .answerVoteId(answerVote.getAnswerVoteId())
            .votes(answerVote.getAnswer().getVotes())
            .build();

        return response;
    }
}
