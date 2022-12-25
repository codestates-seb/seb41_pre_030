package Be_30.Project.vote.service;

import Be_30.Project.answer.entity.Answer;
import Be_30.Project.answer.repository.AnswerRepository;
import Be_30.Project.answer.service.AnswerService;
import Be_30.Project.member.entity.Member;
import Be_30.Project.member.repository.MemberRepository;
import Be_30.Project.member.service.MemberService;
import Be_30.Project.vote.entity.AnswerVote;
import Be_30.Project.vote.repository.AnswerVoteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AnswerVoteService {

    private final AnswerRepository answerRepository;
    private final AnswerVoteRepository answerVoteRepository;
    private final MemberRepository memberRepository;
    private final AnswerService answerService;
    private final MemberService memberService;

    public AnswerVoteService(AnswerRepository answerRepository,
        AnswerVoteRepository answerVoteRepository, MemberRepository memberRepository,
        AnswerService answerService, MemberService memberService) {
        this.answerRepository = answerRepository;
        this.answerVoteRepository = answerVoteRepository;
        this.memberRepository = memberRepository;
        this.answerService = answerService;
        this.memberService = memberService;
    }

    public AnswerVote addVoteUp(long answerId) {
        // 1. answerId로 answer 가져오기?
        Answer answer = answerService.findVerifiedAnswer(answerId);
        // 삭제해야 함
        Member member = memberService.findMember(1L);

        // 2. member 객체와 answer 객체를 이용해 이미 추천/비추천 유무 파악
        if (findCountOfMemberVotesAnswer(answer, member)) {
            // 추천한 적이 없음 -> answer-> votes++
            answer.setVotes(answer.getVotes() + 1);
        }

        // 3. answerVote에 변경된 answer 객체를 주입하고 저장
        Answer saveAnswer = answerRepository.save(answer);
        // 멤버에 setAnswer 필요??
        Member saveMember = memberRepository.save(member);

        AnswerVote answerVote = new AnswerVote();
        answerVote.setAnswer(saveAnswer);
        answerVote.setMember(saveMember);

        return answerVoteRepository.save(answerVote);
    }

    public AnswerVote addVoteDown(long answerId) {
        Answer answer = answerService.findVerifiedAnswer(answerId);
        // 삭제해야 함
        Member member = memberService.findMember(1L);

        if (findCountOfMemberVotesAnswer(answer, member)) {
            answer.setVotes(answer.getVotes() - 1);
        }
        Answer saveAnswer = answerRepository.save(answer);
        // 멤버에 setAnswer 필요??
        Member saveMember = memberRepository.save(member);
        AnswerVote answerVote = new AnswerVote();
        answerVote.setAnswer(saveAnswer);
        answerVote.setMember(saveMember);

        return answerVoteRepository.save(answerVote);
    }

    // 이미 추천/비추천을 한 회원을 검증하는 메서드
    // find
    private boolean findCountOfMemberVotesAnswer(Answer answer, Member member) {
        Optional<AnswerVote> optionalAnswerVote =
            answerVoteRepository.findByAnswerAndMember(answer, member);

        if(optionalAnswerVote.isEmpty()) {
            return true;
        } else {
            // db에 존재하는 내역을 삭제하고 false 반환
            answerVoteRepository.delete(optionalAnswerVote.get());
            return false;
        }
    }
}
