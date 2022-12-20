package Be_30.Project.question.service;

import Be_30.Project.question.entity.Question;
import Be_30.Project.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Question createQuestion(Question question) {
        // 질문 생성
        return null;
    }

    public void deleteQuestion(long questionId) {
        // 질문 삭제
    }

    public Question findQuestion(long questionId) {
        // 질문 조회
        // 조회할 때 마다 조회수 + 1

        return null;
    }

    public List<Question> findQuestionList() {
        // 질문 목록 조회
        return null;
    }

    public void voteQuestion() {
        // 질문 투표(좋아요/싫어요)
    }

    public Question updateQuestion(Question question) {
        // 질문 수정
        return null;
    }
}
