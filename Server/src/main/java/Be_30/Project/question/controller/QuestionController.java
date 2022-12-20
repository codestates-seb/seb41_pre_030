package Be_30.Project.question.controller;

import Be_30.Project.question.dto.QuestionDto;
import Be_30.Project.question.entity.Question;
import Be_30.Project.question.mapper.QuestionMapper;
import Be_30.Project.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper mapper;

    @GetMapping
    public ResponseEntity<List<QuestionDto.Response>> getQuestions() {
        List<Question> questionList = questionService.findQuestionList();
        List<QuestionDto.Response> response = mapper.questionsToQuestionResponseDtos(questionList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDto.Response> getQuestion(@PathVariable long questionId) {
        Question question = questionService.findQuestion(questionId);
        QuestionDto.Response response = mapper.questionToQuestionResponseDto(question);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<QuestionDto.Response> createQuestion(@RequestBody QuestionDto.Post postDto) {
        Question question = mapper.questionPostDtoToQuestion(postDto);
        Question createdQuestion = questionService.createQuestion(question);

        QuestionDto.Response response = mapper.questionToQuestionResponseDto(createdQuestion);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{questionId}")
    public ResponseEntity<QuestionDto.Response> updateQuestion(@PathVariable long questionId,
                                 @RequestBody QuestionDto.Patch patchDto) {
        patchDto.setQuestionId(questionId);
        Question question = mapper.questionPatchDtoToQuestion(patchDto);
        Question updatedQuestion = questionService.updateQuestion(question);

        QuestionDto.Response response = mapper.questionToQuestionResponseDto(updatedQuestion);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<String> deleteQuestion(@PathVariable long questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("삭제 완료");
    }
}
