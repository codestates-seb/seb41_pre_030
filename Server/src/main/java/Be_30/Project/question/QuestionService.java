package Be_30.Project.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public void createQuestion() {
        // 질문 생성
    }

    public void deleteQuestion() {
        // 질문 삭제
    }

    public void findQuestion() {
        // 질문 조회
        // 조회할 때 마다 조회수 + 1
    }

    public void findQuestionList() {
        // 질문 목록 조회
    }

    public void voteQuestion() {
        // 질문 투표(좋아요/싫어요)
    }

    public void updateQuestion() {
        // 질문 수정
    }
}
