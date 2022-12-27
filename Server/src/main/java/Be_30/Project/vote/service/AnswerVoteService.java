package Be_30.Project.vote.service;

import Be_30.Project.answer.entity.Answer;
import Be_30.Project.answer.repository.AnswerRepository;
import Be_30.Project.answer.service.AnswerService;
import Be_30.Project.exception.BusinessLogicException;
import Be_30.Project.exception.ExceptionCode;
import Be_30.Project.member.entity.Member;
import Be_30.Project.member.repository.MemberRepository;
import Be_30.Project.member.service.MemberService;
import Be_30.Project.vote.entity.AnswerVote;
import Be_30.Project.vote.entity.AnswerVote.VoteStatus;
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

    public AnswerVote addVoteUp(long answerId, long memberId) {
        // 1. answerId로 answer 가져오기?
        Answer answer = answerService.findVerifiedAnswer(answerId);
        Member member = memberRepository.findById(memberId).get();

        // 2. member 객체와 answer 객체를 이용해 이미 추천/비추천 유무 파악
        if (VerifyOfMemberVotesAnswer(answer, member)) {
            // 추천한 적이 없음 -> answer-> votes++
            answer.setVotes(answer.getVotes() + 1);
        } else { // 추천한 적이 있음-> 추천인지 비추천인지 확인해야 함
            AnswerVote findAnswerVote = findVerifiedAnswerVote(answer,member);
            if(findAnswerVote.getVoteStatus().equals(VoteStatus.VOTE_UP)) {
                answer.setVotes(answer.getVotes() - 1);
            }else {
                answer.setVotes(answer.getVotes() + 2);
            }
            answerVoteRepository.delete(findAnswerVote);
        }

        // 3. answerVote에 변경된 answer 객체를 주입하고 저장
        Answer saveAnswer = answerRepository.save(answer);

        AnswerVote answerVote = new AnswerVote();
        answerVote.setAnswer(saveAnswer);
        answerVote.setMember(member);
        answerVote.setVoteStatus(VoteStatus.VOTE_UP);

        return answerVoteRepository.save(answerVote);
    }

    public AnswerVote addVoteDown(long answerId, long memberId) {
        Answer answer = answerService.findVerifiedAnswer(answerId);
        Member member = memberRepository.findById(memberId).get();

        if (VerifyOfMemberVotesAnswer(answer, member)) {
            answer.setVotes(answer.getVotes() - 1);
        } else {
            AnswerVote findAnswerVote = findVerifiedAnswerVote(answer, member);
            if(findAnswerVote.getVoteStatus().equals(VoteStatus.VOTE_DOWN)) {
                answer.setVotes(answer.getVotes() + 1);
            } else {
                answer.setVotes(answer.getVotes() - 2);
            }
            answerVoteRepository.delete(findAnswerVote);
        }
        Answer saveAnswer = answerRepository.save(answer);

        AnswerVote answerVote = new AnswerVote();
        answerVote.setAnswer(saveAnswer);
        answerVote.setMember(member);
        answerVote.setVoteStatus(VoteStatus.VOTE_DOWN);

        return answerVoteRepository.save(answerVote);
    }

    // 추천, 비추천 내역이 없음을 검증하는 메서드
    private boolean VerifyOfMemberVotesAnswer(Answer answer, Member member) {
        Optional<AnswerVote> optionalAnswerVote =
            answerVoteRepository.findByAnswerAndMember(answer, member);

        if (optionalAnswerVote.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    // 내역이 있는 vote를 검증하는 메서드?
    private AnswerVote findVerifiedAnswerVote(Answer answer, Member member) {
        Optional<AnswerVote> optionalAnswerVote =
            answerVoteRepository.findByAnswerAndMember(answer, member);

        AnswerVote answerVote =
            optionalAnswerVote.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.VOTE_NOT_FOUND)
            );

        return answerVote;
    }
}
