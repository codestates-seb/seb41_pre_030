package Be_30.Project.answer.service;

import static java.lang.Boolean.TRUE;
import static java.lang.Boolean.valueOf;

import Be_30.Project.answer.entity.Answer;
import Be_30.Project.answer.repository.AnswerRepository;
import Be_30.Project.exception.BusinessLogicException;
import Be_30.Project.exception.ExceptionCode;
import Be_30.Project.member.entity.Member;
import Be_30.Project.member.service.MemberService;
import Be_30.Project.question.entity.Question;
import Be_30.Project.question.service.QuestionService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final MemberService memberService;
    private final QuestionService questionService;

    public AnswerService(AnswerRepository answerRepository, MemberService memberService,
        QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.memberService = memberService;
        this.questionService = questionService;
    }

    // 질문 생성
    public Answer createAnswer(Answer answer, long memberId, String email, long questionId) {

        Question question = questionService.findQuestion(questionId);
        Member member = memberService.findMember(memberId, email);

        answer.setVotes(0);
        answer.setAdopt(false);
        answer.setMember(member);
        answer.setQuestion(question);

        question.getAnswers().add(answer);

        return answerRepository.save(answer);
    }

    // 질문 수정
    public Answer updateAnswer(Answer answer, long memberId) {
        // 해당 질문이 존재하는지 확인
        Answer findAnswer = findVerifiedAnswer(answer.getAnswerId());
        // 해당 질문의 작성자와 memberDetail의 멤버가 같은지 확인
        if(findAnswer.getMember().getMemberId() != memberId) {
            throw new BusinessLogicException(ExceptionCode.NOT_AUTHORIZED);
        }
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
            answerRepository.findAll(PageRequest.of(page - 1, size, Sort.by("votes").ascending()));

        return answerPage;
    }

    // 질문 삭제
    public void deleteAnswer(long answerId, long memberId) {
        Answer answer = findVerifiedAnswer(answerId);
        answer.getQuestion().getAnswers().remove(answer);

        if(answer.getMember().getMemberId() != memberId) {
            throw new BusinessLogicException(ExceptionCode.NOT_AUTHORIZED);
        }
        answerRepository.delete(answer);
    }

    // 질문 채택
    public Answer adoptAnswer(long answerId, long memberId) {
        // 1. answer 존재 유무 검증
        Answer findAnswer = findVerifiedAnswer(answerId);
        VerifiedQuestionAuthor(findAnswer, memberId);
        // 2. answer.getQuestion().getAnswers
        // 3. 각 answer에 accepted 값 확인
        List<Answer> list = findAnswer
            .getQuestion()
            .getAnswers()
            .stream()
            .filter(answer -> valueOf(answer.isAdopt()).equals(TRUE))
            .collect(Collectors.toList());

        if (list.isEmpty()) {
            findAnswer.setAdopt(true);
        } else {
            // list에 answer가 존재한다면,
            // 1. answerId가 동일한 것 -> 채택 취소
            // 2. answerId가 동일하지 않은 것 -> exception 던지기
            boolean adopt = list.stream().allMatch(answer -> answer.getAnswerId().equals(answerId));
            if (adopt) {
                findAnswer.setAdopt(false);
            } else {
                throw new BusinessLogicException(ExceptionCode.ADOPT_NOT_ALLOWED);
            }
        }
        return answerRepository.save(findAnswer);
    }

    // 해당 질문 존재 유뮤 검증 findById 사용, 존재하지 않으면 exception
    public Answer findVerifiedAnswer(long answerId) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);

        Answer answer = optionalAnswer.orElseThrow(
            () -> new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND)
        );

        return answer;
    }

    // 답변이 달린 질문의 작성자를 검증하는 메서드
    private void VerifiedQuestionAuthor(Answer answer, long memberId) {
        Member questionAuthor = answer.getQuestion().getMember();

        if (questionAuthor.getMemberId() != memberId) {
            throw new BusinessLogicException(ExceptionCode.ADOPT_NOT_ALLOWED);
        }
    }

}
