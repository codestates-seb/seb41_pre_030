package Be_30.Project.question.repository;

import Be_30.Project.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    // 제목 기준으로 검색 (대소문자 구분 X)
    Page<Question> findBySubjectContainingIgnoreCaseOrContentContainingIgnoreCase(Pageable pageable, String subject, String content);
}
