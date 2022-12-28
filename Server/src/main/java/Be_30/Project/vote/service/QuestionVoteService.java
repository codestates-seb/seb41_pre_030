package Be_30.Project.vote.service;

import Be_30.Project.answer.entity.Answer;
import Be_30.Project.auth.userdetails.MemberDetails;
import Be_30.Project.exception.BusinessLogicException;
import Be_30.Project.exception.ExceptionCode;
import Be_30.Project.member.entity.Member;
import Be_30.Project.member.repository.MemberRepository;
import Be_30.Project.member.service.MemberService;
import Be_30.Project.question.entity.Question;
import Be_30.Project.question.repository.QuestionRepository;
import Be_30.Project.question.service.QuestionService;
import Be_30.Project.vote.entity.QuestionVote;
import Be_30.Project.vote.entity.QuestionVote.VoteStatus;
import Be_30.Project.vote.repository.QuestionVoteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QuestionVoteService {

    private final QuestionRepository questionRepository;
    private final QuestionVoteRepository questionVoteRepository;
    private final QuestionService questionService;
    private final MemberService memberService;

    public QuestionVoteService(QuestionRepository questionRepository,
        QuestionVoteRepository questionVoteRepository,
        QuestionService questionService, MemberService memberService) {
        this.questionRepository = questionRepository;
        this.questionVoteRepository = questionVoteRepository;
        this.questionService = questionService;
        this.memberService = memberService;
    }

    public QuestionVote addVoteUp(long questionId, MemberDetails memberDetails) {
        Question question = questionService.findQuestion(questionId);
        Member member = memberService.findMember(memberDetails.getMemberId(), memberDetails.getEmail());

        if(VerifyOfMemberVotesQuestion(question, member)) {
            question.makeUpVote();
        } else {
            QuestionVote findQuestionVote = findVerifiedQuestionVote(question, member);
            if(findQuestionVote.getVoteStatus().equals(VoteStatus.VOTE_UP)) {
                question.makeDownVote();
            } else {
                question.makeUpVote();
                question.makeUpVote();
            }
            questionVoteRepository.delete(findQuestionVote);
        }
        Question saveQuestion = questionRepository.save(question);

        QuestionVote questionVote = new QuestionVote();
        questionVote.setQuestion(saveQuestion);
        questionVote.setMember(member);
        questionVote.setVoteStatus(VoteStatus.VOTE_UP);

        return questionVoteRepository.save(questionVote);
    }
    public QuestionVote addVoteDown(long questionId, MemberDetails memberDetails) {
        Question question = questionService.findQuestion(questionId);
        Member member = memberService.findMember(memberDetails.getMemberId(), memberDetails.getEmail());

        if(VerifyOfMemberVotesQuestion(question, member)) {
            question.makeDownVote();
        }else {
            QuestionVote findQuestionVote = findVerifiedQuestionVote(question, member);
            if(findQuestionVote.getVoteStatus().equals(VoteStatus.VOTE_DOWN)) {
                question.makeUpVote();
            } else {
                question.makeDownVote();
                question.makeDownVote();
            }
        }
        Question saveQuestion = questionRepository.save(question);

        QuestionVote questionVote = new QuestionVote();
        questionVote.setQuestion(saveQuestion);
        questionVote.setMember(member);
        questionVote.setVoteStatus(VoteStatus.VOTE_DOWN);

        return questionVoteRepository.save(questionVote);
    }
    private boolean VerifyOfMemberVotesQuestion(Question question, Member member) {
        Optional<QuestionVote> optionalQuestionVote =
            questionVoteRepository.findByQuestionAndMember(question, member);

        if(optionalQuestionVote.isEmpty()) {
            return true;
        }else {
            return false;
        }
    }
    private QuestionVote findVerifiedQuestionVote(Question question, Member member) {
        Optional<QuestionVote> optionalQuestionVote =
            questionVoteRepository.findByQuestionAndMember(question, member);

        QuestionVote questionVote =
            optionalQuestionVote.orElseThrow(
                ()->new BusinessLogicException(ExceptionCode.VOTE_NOT_FOUND)
            );
        return questionVote;
    }
}
