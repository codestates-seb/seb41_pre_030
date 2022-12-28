package Be_30.Project.vote.controller;


import Be_30.Project.auth.userdetails.MemberDetails;
import Be_30.Project.dto.SingleResponseDto;
import Be_30.Project.vote.dto.AnswerVoteResponseDto;
import Be_30.Project.vote.entity.AnswerVote;
import Be_30.Project.vote.mapper.AnswerVoteMapper;
import Be_30.Project.vote.service.AnswerVoteService;
import javax.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
@Validated
public class AnswerVoteController {

    private final AnswerVoteService answerVoteService;
    private final AnswerVoteMapper mapper;

    public AnswerVoteController(AnswerVoteService answerVoteService, AnswerVoteMapper mapper) {
        this.answerVoteService = answerVoteService;
        this.mapper = mapper;
    }

    @PostMapping("/{question-id}/answers/{answer-id}/vote-up")
    public ResponseEntity postVoteUp(@PathVariable("question-id") @Positive long questionId,
        @PathVariable("answer-id") @Positive long answerId,
        @AuthenticationPrincipal MemberDetails memberDetails) {

        if(memberDetails != null) {
            AnswerVote answerVote = answerVoteService.addVoteUp(answerId, memberDetails);
            AnswerVoteResponseDto response = mapper.AnswerVoteToAnswerVoteResponseDto(answerVote);
            return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{question-id}/answers/{answer-id}/vote-down")
    public ResponseEntity postVoteDown(@PathVariable("question-id") @Positive long questionId,
        @PathVariable("answer-id") @Positive long answerId,
        @AuthenticationPrincipal MemberDetails memberDetails) {
        if(memberDetails != null) {
            AnswerVote answerVote = answerVoteService.addVoteDown(answerId, memberDetails);
            AnswerVoteResponseDto response = mapper.AnswerVoteToAnswerVoteResponseDto(answerVote);
            return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
