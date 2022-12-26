package Be_30.Project.vote.controller;

import Be_30.Project.dto.SingleResponseDto;
import Be_30.Project.vote.dto.AnswerVoteResponseDto;
import Be_30.Project.vote.dto.QuestionVoteResponseDto;
import Be_30.Project.vote.entity.AnswerVote;
import Be_30.Project.vote.entity.QuestionVote;
import Be_30.Project.vote.mapper.AnswerVoteMapper;
import Be_30.Project.vote.mapper.QuestionVoteMapper;
import Be_30.Project.vote.service.AnswerVoteService;
import Be_30.Project.vote.service.QuestionVoteService;
import javax.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
@Validated
public class QuestionVoteController {

    private final QuestionVoteService questionVoteService;
    private final QuestionVoteMapper mapper;

    public QuestionVoteController(QuestionVoteService questionVoteService, QuestionVoteMapper mapper) {
        this.questionVoteService = questionVoteService;
        this.mapper = mapper;
    }

    @PostMapping("/{question-id}/vote-up")
    public ResponseEntity postVoteUp(@PathVariable("question-id") @Positive long questionId) {
        QuestionVote questionVote = questionVoteService.addVoteUp(questionId);

        QuestionVoteResponseDto response = mapper.QuestionVoteToQuestionVoteResponseDto(questionVote);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @PostMapping("/{question-id}/vote-down")
    public ResponseEntity postVoteDown(@PathVariable("question-id") @Positive long questionId) {
        QuestionVote questionVote = questionVoteService.addVoteDown(questionId);

        QuestionVoteResponseDto response = mapper.QuestionVoteToQuestionVoteResponseDto(questionVote);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }
}
