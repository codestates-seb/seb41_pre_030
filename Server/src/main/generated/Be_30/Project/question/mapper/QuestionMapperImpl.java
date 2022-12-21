package Be_30.Project.question.mapper;

import Be_30.Project.question.dto.QuestionDto.Patch;
import Be_30.Project.question.dto.QuestionDto.Post;
import Be_30.Project.question.dto.QuestionDto.Response;
import Be_30.Project.question.dto.QuestionDto.Response.ResponseBuilder;
import Be_30.Project.question.entity.Question;
import Be_30.Project.question.entity.Question.QuestionBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-20T22:52:05+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.16.1 (Eclipse Adoptium)"
)
@Component
public class QuestionMapperImpl implements QuestionMapper {

    @Override
    public Question questionPostDtoToQuestion(Post questionPostDto) {
        if ( questionPostDto == null ) {
            return null;
        }

        QuestionBuilder question = Question.builder();

        question.subject( questionPostDto.getSubject() );
        question.content( questionPostDto.getContent() );

        return question.build();
    }

    @Override
    public Question questionPatchDtoToQuestion(Patch questionPatchDto) {
        if ( questionPatchDto == null ) {
            return null;
        }

        QuestionBuilder question = Question.builder();

        question.questionId( questionPatchDto.getQuestionId() );
        question.subject( questionPatchDto.getSubject() );
        question.content( questionPatchDto.getContent() );

        return question.build();
    }

    @Override
    public Response questionToQuestionResponseDto(Question question) {
        if ( question == null ) {
            return null;
        }

        ResponseBuilder response = Response.builder();

        response.questionId( question.getQuestionId() );
        response.subject( question.getSubject() );
        response.content( question.getContent() );
        response.vote( question.getVote() );
        response.view( question.getView() );
        response.createdAt( question.getCreatedAt() );
        response.modifiedAt( question.getModifiedAt() );

        return response.build();
    }

    @Override
    public List<Response> questionsToQuestionResponseDtos(List<Question> questions) {
        if ( questions == null ) {
            return null;
        }

        List<Response> list = new ArrayList<Response>( questions.size() );
        for ( Question question : questions ) {
            list.add( questionToQuestionResponseDto( question ) );
        }

        return list;
    }
}
