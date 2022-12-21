package Be_30.Project.question.service;

import Be_30.Project.question.entity.Question;
import Be_30.Project.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Question createQuestion(Question question) {
        // 질문 생성
        return questionRepository.save(question);
    }

    public void deleteQuestion(long questionId) {
        // 질문 삭제
    }

    public Question findQuestion(long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("질문이 없습니다"));
    }

    public List<Question> findQuestionList() {
        return questionRepository.findAll();
    }

    public void voteQuestion() {
        // 질문 투표(좋아요/싫어요)
    }

    public Question updateQuestion(Question question) {
        Question findQuestion = questionRepository.findById(question.getQuestionId())
                .orElseThrow(() -> new EntityNotFoundException("질문이 없습니다"));
        findQuestion.updateQuestion(question.getSubject(), question.getContent());
        return findQuestion;
    }
}
