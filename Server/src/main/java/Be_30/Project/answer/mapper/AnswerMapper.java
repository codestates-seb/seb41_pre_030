package Be_30.Project.answer.mapper;

import Be_30.Project.answer.dto.AnswerDto;
import Be_30.Project.answer.dto.AnswerDto.Response;
import Be_30.Project.answer.entity.Answer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    //postDto -> 객체
    Answer answerPostDtoToAnswer(AnswerDto.Post answerPostDto);
    // patchDto -> 객체
    Answer answerPatchDtoToAnswer(AnswerDto.Patch answerPatchDto);
    // 객체 -> responseDto
    default AnswerDto.Response answerToAnswerResponseDto(Answer answer) {
        if ( answer == null ) {
            return null;
        }

        Long answerId = null;
        String content = null;
        boolean adopt = false;
        int votes = 0;
        LocalDateTime createdAt = null;
        LocalDateTime modifiedAt = null;

        answerId = answer.getAnswerId();
        content = answer.getContent();
        adopt = answer.isAdopt();
        votes = answer.getVotes();
        createdAt = answer.getCreatedAt();
        modifiedAt = answer.getModifiedAt();

        long memberId = answer.getMember().getMemberId();
        long questionId = answer.getQuestion().getQuestionId();

        Response response = new Response( answerId, content, adopt, votes, memberId, questionId, createdAt, modifiedAt );

        return response;
    }
    default List<AnswerDto.Response> answersToAnswerResponseDtos(List<Answer> answers) {
        if ( answers == null ) {
            return null;
        }

        List<Response> list = new ArrayList<Response>( answers.size() );
        for ( Answer answer : answers ) {
            list.add( answerToAnswerResponseDto( answer ) );
        }

        return list;
    }
}
