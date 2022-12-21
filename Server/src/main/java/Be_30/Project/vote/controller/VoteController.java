package Be_30.Project.vote.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/votes")
public class VoteController {
    // post
    @PostMapping
    public ResponseEntity postVote() {
        // post -> boolean? false에서 true?
        return null;
    }
    // delete
}
