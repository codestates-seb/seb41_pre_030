package Be_30.Project.vote.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answer/votes")
public class AnswerVoteController {
    @PostMapping //("/{answer-id}")
    public ResponseEntity postVote() {
        // 사용자 정보와, 추천수를 등록할 답변 ID
        // 이미 추천을 한 사람은 또 추천 불가능!
        return null;
    }
}
