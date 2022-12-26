package Be_30.Project.vote.mapper;

import Be_30.Project.vote.dto.AnswerVoteResponseDto;
import Be_30.Project.vote.dto.QuestionVoteResponseDto;
import Be_30.Project.vote.entity.QuestionVote;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionVoteMapper {
    default QuestionVoteResponseDto QuestionVoteToQuestionVoteResponseDto (QuestionVote questionVote) {
        if(questionVote == null) {
            return null;
        }
        QuestionVoteResponseDto response = QuestionVoteResponseDto.builder()
            .questionVoteId(questionVote.getQuestionVoteId())
            .votes(questionVote.getQuestion().getVotes())
            .build();

        return response;
    }
}
