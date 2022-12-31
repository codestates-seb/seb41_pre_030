package Be_30.Project.question.controller;

import Be_30.Project.auth.userdetails.MemberDetails;
import Be_30.Project.dto.MultiResponseDto;
import Be_30.Project.dto.SingleResponseDto;
import Be_30.Project.question.dto.QuestionDto;
import Be_30.Project.question.entity.Question;
import Be_30.Project.question.mapper.QuestionMapper;
import Be_30.Project.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
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

    @GetMapping("/{questionId}")
    public ResponseEntity<?> getQuestion(@Positive @PathVariable long questionId) {
        Question question = questionService.findQuestion(questionId);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.questionToQuestionResponseDto(question)),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<?> createQuestion(@Valid @RequestBody QuestionDto.Post postDto,
                                            @AuthenticationPrincipal MemberDetails member) {
        Question question = mapper.questionPostDtoToQuestion(postDto);
        Question createdQuestion = questionService.createQuestion(
                question, member.getMemberId(), member.getEmail()
        );
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
}
