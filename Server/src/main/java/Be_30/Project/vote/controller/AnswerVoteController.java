package Be_30.Project.vote.controller;

import javax.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answers")
@Validated
public class AnswerVoteController {
    // 추천
    @PostMapping("/{answer-id}/vote-up")
    public ResponseEntity postVoteUp(@PathVariable("answer-id") @Positive long answerId) { // 추천을 한 사용자의 정보는??
        // 해당 질문에 대한 추천수를 변경해야함!

        return null; // 넘길것은 추천수! 와 answerId, MemberId?
    }
    // 비추천
    @PostMapping("/{answer-id}/vote-down")
    public ResponseEntity postVoteDown(@PathVariable("answer-id") @Positive long answerId) {
        return null;
    }
}
