package Be_30.Project.vote.controller;

import Be_30.Project.vote.service.AnswerVoteService;
import javax.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answers")
@Validated
public class AnswerVoteController {

    private final AnswerVoteService answerVoteService;

    public AnswerVoteController(AnswerVoteService answerVoteService) {
        this.answerVoteService = answerVoteService;
    }

    @PostMapping("/{answer-id}/vote-up")
    public ResponseEntity postVoteUp(
        @PathVariable("answer-id") @Positive long answerId) { // 추천을 한 사용자의 정보를 받아와야? 지금 누른 사람은 로그인한 사람!
        // 1. 파라미터로 받은 answerId와 사용자 정보를 파라미터로 하여 서비스 로직을 호출
        answerVoteService.addVoteUp(answerId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{answer-id}/vote-down")
    public ResponseEntity postVoteDown(@PathVariable("answer-id") @Positive long answerId) {
        answerVoteService.addVoteDown(answerId);
        // 반환할 값이 있어야 하나?
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
