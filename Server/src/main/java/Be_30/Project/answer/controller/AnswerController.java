package Be_30.Project.answer.controller;

import Be_30.Project.answer.dto.AnswerDto;
import Be_30.Project.answer.entity.Answer;
import Be_30.Project.answer.mapper.AnswerMapper;
import Be_30.Project.answer.service.AnswerService;
import Be_30.Project.auth.userdetails.MemberDetails;
import Be_30.Project.dto.MultiResponseDto;
import Be_30.Project.dto.SingleResponseDto;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/questions")
@Validated
@Slf4j
public class AnswerController {

    private final AnswerService answerService;
    private final AnswerMapper mapper;

    public AnswerController(AnswerService answerService, AnswerMapper mapper) {
        this.answerService = answerService;
        this.mapper = mapper;
    }

    @PostMapping("/{question-id}/answers")
    public ResponseEntity postAnswer(@PathVariable("question-id") @Positive long questionId,
        @Valid @RequestBody AnswerDto.Post answerPostDto,
        @AuthenticationPrincipal MemberDetails memberDetails) {
        // 전달 받은 Dto -> 서비스에 보냄
        Answer answer = answerService.createAnswer(mapper.answerPostDtoToAnswer(answerPostDto),
            memberDetails.getMemberId(), memberDetails.getEmail(), questionId);

        URI location = UriComponentsBuilder.newInstance()
            .path("/questions/{question-id}/answers/{answer-id}")
            .buildAndExpand(questionId, answer.getAnswerId())
            .toUri();

        log.info("Post Answer Location: " + location.toString());

        return ResponseEntity.created(location).build();
    }

    // 채택 기능
    @PostMapping("/{question-id}/answers/{answer-id}/adopt")
    public ResponseEntity postAdopt(@PathVariable("question-id") @Positive long questionId,
        @PathVariable("answer-id") @Positive long answerId,
        @AuthenticationPrincipal MemberDetails memberDetails) {
        Answer answer = answerService.adoptAnswer(answerId, memberDetails.getMemberId());

        AnswerDto.Response response = mapper.answerToAnswerResponseDto(answer);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @PatchMapping("/{question-id}/answers/{answer-id}")
    public ResponseEntity patchAnswer(@PathVariable("question-id") @Positive long questionId,
        @PathVariable("answer-id") @Positive long answerId,
        @Valid @RequestBody AnswerDto.Patch answerPatchDto,
        @AuthenticationPrincipal MemberDetails memberDetails) {
        // dto -> 객체 -> setId
        Answer answer = mapper.answerPatchDtoToAnswer(answerPatchDto);
        answer.setAnswerId(answerId);
        // 객체 -> service
        Answer updateAnswer = answerService.updateAnswer(answer, memberDetails.getMemberId());
        // 반환받은 객체 -> responseDto
        AnswerDto.Response response = mapper.answerToAnswerResponseDto(updateAnswer);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping("/{question-id}/answers/{answer-id}")
    public ResponseEntity getAnswer(@PathVariable("question-id") @Positive long questionId,
        @PathVariable("answer-id") @Positive long answerId) {
        // id로 service의 findAnswers 조회
        Answer answer = answerService.findAnswer(answerId);

        AnswerDto.Response response = mapper.answerToAnswerResponseDto(answer);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping("/{question-id}/answers")
    public ResponseEntity getAnswers(@PathVariable("question-id") @Positive long questionId,
        @Positive @RequestParam(defaultValue = "1") int page,
        @Positive @RequestParam(defaultValue = "10") int size) {

        Page<Answer> answerPage = answerService.findAnswers(page, size);

        // 질문 객체
        List<Answer> answers = answerPage.getContent();
        List<AnswerDto.Response> responses = mapper.answersToAnswerResponseDtos(answers);

        return new ResponseEntity<>(new MultiResponseDto<>(responses, answerPage), HttpStatus.OK);
    }

    @DeleteMapping("/{question-id}/answers/{answer-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnswer(@PathVariable("question-id") @Positive long questionId,
        @PathVariable("answer-id") @Positive long answerId,
        @AuthenticationPrincipal MemberDetails memberDetails) {

        answerService.deleteAnswer(answerId, memberDetails.getMemberId());

    }

}
