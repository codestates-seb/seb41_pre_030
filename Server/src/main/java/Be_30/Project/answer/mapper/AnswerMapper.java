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
    AnswerDto.Response answerToAnswerResponseDto(Answer answer);
    List<AnswerDto.Response> answersToAnswerResponseDtos(List<Answer> answers);
}
