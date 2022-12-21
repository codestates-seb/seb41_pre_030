package Be_30.Project.question;

import Be_30.Project.question.dto.QuestionPatchDto;
import Be_30.Project.question.dto.QuestionPostDto;
import Be_30.Project.question.dto.QuestionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    public List<QuestionResponseDto> getQuestions() {
        return List.of(
                createTestResponse(1L),
                createTestResponse(2L),
                createTestResponse(3L)
        );
    }

    @GetMapping("/{questionId}")
    public QuestionResponseDto getQuestion(@PathVariable long questionId) {
        return createTestResponse(questionId);
    }

    @PostMapping
    public String createQuestion(@RequestBody QuestionPostDto postDto) {
        return "질문 등록";
    }

    @PatchMapping("/{questionId}")
    public String updateQuestion(@PathVariable long questionId,
                                 @RequestBody QuestionPatchDto patchDto) {
        System.out.println(patchDto.toString());
        return questionId + "번 질문 수정";
    }

    @DeleteMapping
    public String deleteQuestion() {
        return "질문 삭제";
    }


    // 테스트용
   private QuestionResponseDto createTestResponse(long questionId) {
        return QuestionResponseDto.builder()
                .questionId(questionId)
                .subject("질문 제목")
                .content("질문 내용")
                .view(123)
                .vote(4)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
    }
}
