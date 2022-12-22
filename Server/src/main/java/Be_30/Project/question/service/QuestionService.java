package Be_30.Project.question.service;

import Be_30.Project.exception.BusinessLogicException;
import Be_30.Project.exception.ExceptionCode;
import Be_30.Project.question.entity.Question;
import Be_30.Project.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class QuestionService {

    private final VoteService voteService;
    private final QuestionRepository questionRepository;

    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    // 그냥 단순 조회
    private Question findById(long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
    }

    // 조회수 + 1 시키는 질문 조회
    public Question findQuestion(long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
        question.viewQuestion(); // 질문 조회수 + 1
        return question;
    }

    public Page<Question> findQuestions(int page, int size, String searchValue, String tab) {
        // tab 종류 = newest(최신순), active(조회수), votes(투표수)
        Sort sort;
        switch (tab) {
            case "active":
                sort = Sort.by("views", "questionId").descending(); // 조회수 기준 내림차순
                break;
            case "votes":
                sort = Sort.by("votes", "questionId").descending(); // 투표수 기준 내림차순
                break;
            default:
                sort = Sort.by("questionId").descending(); // ID 기준 내림차순
                break;
        }
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        // 비어있거나, 공백만 있는 경우 (검색어가 없는 경우)
        if (searchValue == null || searchValue.isBlank()) {
            return questionRepository.findAll(pageRequest);
        } else { // 검색어가 있는 경우 (제목 또는 본문에 searchValue가 포함되어 있는 질문 검색)
            return questionRepository.findBySubjectContainingIgnoreCaseOrContentContainingIgnoreCase(
                    pageRequest, searchValue, searchValue
            );
        }
    }

    public Question updateQuestion(Question question) {
        Question findQuestion = findById(question.getQuestionId());
        findQuestion.updateQuestion(question.getSubject(), question.getContent());
        return findQuestion;
    }

    public void deleteQuestion(long questionId) {
        Question findQuestion = findById(questionId);
        questionRepository.delete(findQuestion);
    }

    // 게시글 투표
    public Question voteQuestion(long questionId, String voteType) {
        Question question = findById(questionId);
        voteService.makeVote(question, voteType);
        switch (voteType) {
            case "up":
                question.makeUpVote();
                break;
            case "down":
                question.makeDownVote();
                break;
        }
        return question;
    }
}
