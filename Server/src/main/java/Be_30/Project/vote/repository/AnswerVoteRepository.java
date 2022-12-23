package Be_30.Project.vote.repository;

import Be_30.Project.answer.entity.Answer;
import Be_30.Project.member.entity.Member;
import Be_30.Project.vote.entity.AnswerVote;
import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerVoteRepository extends JpaRepository<AnswerVote, Long> {
    // Answer, Member를 파라미터로 받으면서 vote를 한 사용자와 답변을 조회할 수 있는 기능 구현
    List<AnswerVote> findByAnswerAndMember(Answer answer, Member member);
}
