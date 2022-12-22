package Be_30.Project.vote.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/votes")
public class QuestionVoteController {
    @PostMapping //("/{question-id}"
    public ResponseEntity postVote() {
        // 사용자 정보와, 추천수를 등록할 질문 id
        return null;
    }
}
