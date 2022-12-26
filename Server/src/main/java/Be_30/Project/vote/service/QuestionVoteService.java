package Be_30.Project.vote.service;

import Be_30.Project.answer.entity.Answer;
import Be_30.Project.member.entity.Member;
import Be_30.Project.member.repository.MemberRepository;
import Be_30.Project.member.service.MemberService;
import Be_30.Project.question.entity.Question;
import Be_30.Project.question.repository.QuestionRepository;
import Be_30.Project.question.service.QuestionService;
import Be_30.Project.vote.entity.QuestionVote;
import Be_30.Project.vote.repository.QuestionVoteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QuestionVoteService {

    private final QuestionRepository questionRepository;
    private final QuestionVoteRepository questionVoteRepository;
    private final MemberRepository memberRepository;
    private final QuestionService questionService;
    private final MemberService memberService;

    public QuestionVoteService(QuestionRepository questionRepository,
        QuestionVoteRepository questionVoteRepository, MemberRepository memberRepository,
        QuestionService questionService, MemberService memberService) {
        this.questionRepository = questionRepository;
        this.questionVoteRepository = questionVoteRepository;
        this.memberRepository = memberRepository;
        this.questionService = questionService;
        this.memberService = memberService;
    }

    public QuestionVote addVoteUp(long questionId, Member member) {
        Question question = questionService.findQuestion(questionId);

        if(findCountOfMemberVotesQuestion(question, member)) {
            question.makeUpVote();
        } else {
            question.makeDownVote();
        }
        Question saveQuestion = questionRepository.save(question);

        QuestionVote questionVote = new QuestionVote();
        questionVote.setQuestion(saveQuestion);
        questionVote.setMember(member);

        return questionVoteRepository.save(questionVote);
    }
    public QuestionVote addVoteDown(long questionId, Member member) {
        Question question = questionService.findQuestion(questionId);

        if(findCountOfMemberVotesQuestion(question, member)) {
            question.makeDownVote();
        }else {
            question.makeUpVote();
        }
        Question saveQuestion = questionRepository.save(question);

        QuestionVote questionVote = new QuestionVote();
        questionVote.setQuestion(saveQuestion);
        questionVote.setMember(member);

        return questionVoteRepository.save(questionVote);
    }
    private boolean findCountOfMemberVotesQuestion(Question question, Member member) {
        Optional<QuestionVote> optionalQuestionVote =
            questionVoteRepository.findByQuestionAndMember(question, member);

        if(optionalQuestionVote.isEmpty()) {
            return true;
        }else {
            questionVoteRepository.delete(optionalQuestionVote.get());
            return false;
        }
    }
}
