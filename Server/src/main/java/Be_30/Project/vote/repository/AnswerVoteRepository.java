package Be_30.Project.vote.repository;

import Be_30.Project.vote.entity.AnswerVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerVoteRepository extends JpaRepository<AnswerVote, Long> {
    // Answer, Member를 파라미터로 받으면서 vote를 한 사용자와 답변을 조회할 수 있는 기능 구현
}
