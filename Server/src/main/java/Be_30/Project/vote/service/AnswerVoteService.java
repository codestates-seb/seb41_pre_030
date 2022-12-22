package Be_30.Project.vote.service;

import Be_30.Project.answer.entity.Answer;
import Be_30.Project.vote.entity.AnswerVote;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AnswerVoteService {
    // answerRepo DI
    // answerVoteRepo DI
    public AnswerVote addVoteUp() {
        // answerId로 해당 답변 조회 후, vote 값 업데이트 -> 저장
        // 이미 추천을 한 회원이라면, 추천 취소 로직 구현
        return null;
    }
    public AnswerVote addVoteDown() {
        return null;
    }
    // 이미 추천/비추천을 한 회원을 검증하는 메서드
    private Answer findVerifiedAnswerVoteMember(long answerId) {
        return null;
    }
}
