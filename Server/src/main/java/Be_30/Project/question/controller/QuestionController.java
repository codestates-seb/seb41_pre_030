package Be_30.Project.question.controller;

import Be_30.Project.dto.MultiResponseDto;
import Be_30.Project.dto.SingleResponseDto;
import Be_30.Project.question.dto.QuestionDto;
import Be_30.Project.question.entity.Question;
import Be_30.Project.question.mapper.QuestionMapper;
import Be_30.Project.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper mapper;

    @GetMapping
    public ResponseEntity<?> getQuestions(@Positive @RequestParam(defaultValue = "1") int page,
                                          @Positive @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(required = false, name = "q") String searchValue,
                                          @RequestParam(defaultValue = "newest") String tab) {
        Page<Question> pageQuestions = questionService.findQuestions(page - 1, size, searchValue, tab);
        List<Question> questionList = pageQuestions.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(
                        mapper.questionsToQuestionResponseDtos(questionList),
                        pageQuestions),
                HttpStatus.OK
        );
    }

    // TODO: 질문 조회 시 답변 목록도 함게 변환해야 함
    @GetMapping("/{questionId}")
    public ResponseEntity<?> getQuestion(@Positive @PathVariable long questionId) {
        Question question = questionService.findQuestion(questionId);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.questionToQuestionResponseDto(question)),
                HttpStatus.OK
        );
    }

    // TODO: 질문 작성자 정보 추가
    @PostMapping
    public ResponseEntity<?> createQuestion(@Valid @RequestBody QuestionDto.Post postDto) {
        Question question = mapper.questionPostDtoToQuestion(postDto);
        Question createdQuestion = questionService.createQuestion(question);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.questionToQuestionResponseDto(createdQuestion)),
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{questionId}")
    public ResponseEntity<?> updateQuestion(@Positive @PathVariable long questionId,
                                            @Valid @RequestBody QuestionDto.Patch patchDto) {
        patchDto.setQuestionId(questionId);
        Question question = mapper.questionPatchDtoToQuestion(patchDto);
        Question updatedQuestion = questionService.updateQuestion(question);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.questionToQuestionResponseDto(updatedQuestion)),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<?> deleteQuestion(@Positive @PathVariable long questionId) {
        questionService.deleteQuestion(questionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // TODO: 회원 정보를 같이 가져올 수 있어야 함
    @PostMapping("/{questionId}/vote")
    public ResponseEntity<?> voteQuestion(@PathVariable long questionId,
                                          @RequestParam String voteType) {
        Question question = questionService.voteQuestion(questionId, voteType);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.questionToQuestionResponseDto(question)),
                HttpStatus.OK
        );
    }
}
