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

        QuestionVote questionVote = new QuestionVote();

        if(VerifyOfMemberVotesQuestion(question, member)) {

            question.makeUpVote();
            questionVote.setVoteStatus(VoteStatus.VOTE_UP);

        } else {
            QuestionVote findQuestionVote = findVerifiedQuestionVote(question, member);
            VoteStatus voteStatus = findQuestionVote.getVoteStatus();

            if(voteStatus.equals(VoteStatus.VOTE_UP)) {
                question.makeDownVote();
                questionVote.setVoteStatus(VoteStatus.VOTE_CANCEL);
            } else if (voteStatus.equals(VoteStatus.VOTE_DOWN)) {
                question.makeUpVote();
                questionVote.setVoteStatus(VoteStatus.VOTE_CANCEL);
            } else if (voteStatus.equals(VoteStatus.VOTE_CANCEL)) {
                question.makeUpVote();
                questionVote.setVoteStatus(VoteStatus.VOTE_UP);
            }
            questionVoteRepository.delete(findQuestionVote);
        }
        Question saveQuestion = questionRepository.save(question);
        questionVote.setQuestion(saveQuestion);
        questionVote.setMember(member);

        return questionVoteRepository.save(questionVote);
    }
    public QuestionVote addVoteDown(long questionId, MemberDetails memberDetails) {
        Question question = questionService.findQuestion(questionId);
        Member member = memberService.findMember(memberDetails.getMemberId(), memberDetails.getEmail());

        QuestionVote questionVote = new QuestionVote();

        if(VerifyOfMemberVotesQuestion(question, member)) {
            question.makeDownVote();
            questionVote.setVoteStatus(VoteStatus.VOTE_DOWN);
        } else {
            QuestionVote findQuestionVote = findVerifiedQuestionVote(question, member);
            VoteStatus voteStatus = findQuestionVote.getVoteStatus();
            if(voteStatus.equals(VoteStatus.VOTE_DOWN)) {
                question.makeUpVote();
                questionVote.setVoteStatus(VoteStatus.VOTE_CANCEL);
            } else if (voteStatus.equals(VoteStatus.VOTE_UP)) {
                question.makeDownVote();
                questionVote.setVoteStatus(VoteStatus.VOTE_CANCEL);
            } else if (voteStatus.equals(VoteStatus.VOTE_CANCEL)) {
                question.makeDownVote();
                questionVote.setVoteStatus(VoteStatus.VOTE_DOWN);
            }
            questionVoteRepository.delete(findQuestionVote);
        }
        Question saveQuestion = questionRepository.save(question);

        questionVote.setQuestion(saveQuestion);
        questionVote.setMember(member);

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
