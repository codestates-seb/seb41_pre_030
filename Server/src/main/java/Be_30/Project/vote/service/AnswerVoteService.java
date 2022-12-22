package Be_30.Project.vote.service;

import Be_30.Project.answer.entity.Answer;
import Be_30.Project.answer.repository.AnswerRepository;
import Be_30.Project.answer.service.AnswerService;
import Be_30.Project.member.entity.Member;
import Be_30.Project.vote.entity.AnswerVote;
import Be_30.Project.vote.repository.AnswerVoteRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AnswerVoteService {

    private final AnswerRepository answerRepository;
    private final AnswerVoteRepository answerVoteRepository;
    private final AnswerService answerService;

    public AnswerVoteService(AnswerRepository answerRepository,
        AnswerVoteRepository answerVoteRepository, AnswerService answerService) {
        this.answerRepository = answerRepository;
        this.answerVoteRepository = answerVoteRepository;
        this.answerService = answerService;
    }

    public void addVoteUp(long answerId) {
        // 1. answerId로 answer 가져오기?
        Answer answer = answerService.findVerifiedAnswer(answerId);
        // 2. member 객체와 answer 객체를 이용해 이미 추천/비추천 유무 파악
        if (findVerifiedAnswerVoteMember(answer)) {
            // 추천한 적이 없음 -> answer-> votes++
            answer.setVotes(answer.getVotes() + 1);
        } else {
            // 추천한 적이 있음-> answer-> votes--;
            answer.setVotes(answer.getVotes() - 1);
        }
        // 3. answerVote에 변경된 answer 객체를 주입하고 저장
        Answer saveAnswer = answerRepository.save(answer);
        AnswerVote answerVote = new AnswerVote();
        answerVote.setAnswer(saveAnswer);
        // answerVote.setMember(saveMember);

        answerVoteRepository.save(answerVote);
    }

    public void addVoteDown(long answerId) {
        Answer answer = answerService.findVerifiedAnswer(answerId);

        if (findVerifiedAnswerVoteMember(answer)) {
            answer.setVotes(answer.getVotes() - 1);
        } else {
            answer.setVotes(answer.getVotes() + 1);
        }
        Answer saveAnswer = answerRepository.save(answer);
        AnswerVote answerVote = new AnswerVote();
        answerVote.setAnswer(saveAnswer);
        // answerVote.setMember(saveMember);

        answerVoteRepository.save(answerVote);
    }

    // 이미 추천/비추천을 한 회원을 검증하는 메서드
    private boolean findVerifiedAnswerVoteMember(Answer answer) {
        Optional<AnswerVote> optionalAnswerVote =
            answerVoteRepository.findByAnswerAndMember(answer, new Member());

        return optionalAnswerVote.isEmpty();
    }
}
