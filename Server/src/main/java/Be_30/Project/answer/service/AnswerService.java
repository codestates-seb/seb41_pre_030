package Be_30.Project.answer.service;

import Be_30.Project.answer.entity.Answer;
import Be_30.Project.answer.repository.AnswerRepository;
import Be_30.Project.exception.BusinessLogicException;
import Be_30.Project.exception.ExceptionCode;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }
    // 질문 생성
    public Answer createAnswer(Answer answer) {
        // 해당 답변이 존재하는 지?? 중복 허용을 하기 때문에 존재유뮤 확인 x
        // repo에 저장
        answer.setVotes(0);
        answer.setAccepted(false);

        Answer savedAnswer = answerRepository.save(answer);

        return savedAnswer;
    }
    // 질문 수정
    public Answer updateAnswer(Answer answer) {
        // 해당 질문이 존재하는지 확인
        Answer findAnswer = findVerifiedAnswer(answer.getAnswerId());
        // 변경 내용이 null 이 아니라면 변경
        Optional.ofNullable(answer.getContent())
            .ifPresent(content -> findAnswer.setContent(content));

        return answerRepository.save(findAnswer);
    }
    // 질문 조회
    public Answer findAnswer(long answerId) {
        return findVerifiedAnswer(answerId);
    }
    // 질문 목록 조회
    public Page<Answer> findAnswers(int page, int size) {
        Page<Answer> answerPage =
            answerRepository.findAll(PageRequest.of(page-1, size, Sort.by("answerId")));

        return answerPage;
    }
    // 질문 삭제
    public void deleteAnswer(long answerId) {
        Answer answer = findVerifiedAnswer(answerId);
        answerRepository.delete(answer);
    }

    // 해당 질문 존재 유뮤 검증 findById 사용, 존재하지 않으면 exception
    public Answer findVerifiedAnswer(long answerId) {
       Optional<Answer> optionalAnswer = answerRepository.findById(answerId);

       Answer answer = optionalAnswer.orElseThrow(
           () -> new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND)
       );

       return answer;
    }
}
