package Be_30.Project.vote.repository;

import Be_30.Project.member.entity.Member;
import Be_30.Project.question.entity.Question;
import Be_30.Project.vote.entity.QuestionVote;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionVoteRepository extends JpaRepository<QuestionVote, Long> {

    Optional<QuestionVote> findByQuestionAndMember(Question question, Member member);
}
