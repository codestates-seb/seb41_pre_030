package Be_30.Project.question.mapper;

import Be_30.Project.answer.entity.Answer;
import Be_30.Project.member.entity.Member;
import Be_30.Project.member.entity.Member.MemberStatus;
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
    date = "2022-12-22T15:24:57+0900",
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
        response.votes( question.getVotes() );
        response.views( question.getViews() );
        response.createdAt( question.getCreatedAt() );
        response.modifiedAt( question.getModifiedAt() );
        response.member( memberToResponse( question.getMember() ) );
        response.answers( answerListToResponseList( question.getAnswers() ) );

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

    protected Be_30.Project.member.dto.MemberDto.Response memberToResponse(Member member) {
        if ( member == null ) {
            return null;
        }

        long memberId = 0L;
        String email = null;
        String nickName = null;
        MemberStatus memberStatus = null;

        memberId = member.getMemberId();
        email = member.getEmail();
        nickName = member.getNickName();
        memberStatus = member.getMemberStatus();

        Be_30.Project.member.dto.MemberDto.Response response = new Be_30.Project.member.dto.MemberDto.Response( memberId, email, nickName, memberStatus );

        return response;
    }

    protected Be_30.Project.answer.dto.AnswerDto.Response answerToResponse(Answer answer) {
        if ( answer == null ) {
            return null;
        }

        Long answerId = null;
        String content = null;
        boolean accepted = false;
        int answerVote = 0;

        answerId = answer.getAnswerId();
        content = answer.getContent();
        accepted = answer.isAccepted();
        answerVote = answer.getAnswerVote();

        Be_30.Project.answer.dto.AnswerDto.Response response = new Be_30.Project.answer.dto.AnswerDto.Response( answerId, content, accepted, answerVote );

        return response;
    }

    protected List<Be_30.Project.answer.dto.AnswerDto.Response> answerListToResponseList(List<Answer> list) {
        if ( list == null ) {
            return null;
        }

        List<Be_30.Project.answer.dto.AnswerDto.Response> list1 = new ArrayList<Be_30.Project.answer.dto.AnswerDto.Response>( list.size() );
        for ( Answer answer : list ) {
            list1.add( answerToResponse( answer ) );
        }

        return list1;
    }
}
