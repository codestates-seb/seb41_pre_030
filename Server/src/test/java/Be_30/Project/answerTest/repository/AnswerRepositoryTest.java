package Be_30.Project.answerTest.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.hamcrest.Matchers.is;

import Be_30.Project.answer.entity.Answer;
import Be_30.Project.answer.repository.AnswerRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@DataJpaTest
public class AnswerRepositoryTest {
    @Autowired
    private AnswerRepository answerRepository;

    @Test
    void saveAnswerTest() {
        //given
        Answer answer = new Answer("답변", false, 0);
        //when
        Answer saveAnswer = answerRepository.save(answer);
        //then
        assertNotNull(saveAnswer);
        assertThat(answer.getContent(), is(saveAnswer.getContent()));
        assertThat(answer.getAnswerVote(), is(saveAnswer.getAnswerVote()));
        assertThat(answer.isAccepted(), is(saveAnswer.isAccepted()));
    }
    @Test
    void findByAnswerIdTest() {
        //given 저장된 객체
        Answer answer = new Answer("답변", false, 0);
        answer.setAnswerId(1L);

        Answer saveAnswer = answerRepository.save(answer);
        //when
        Answer findAnswer = answerRepository.findById(saveAnswer.getAnswerId()).get();
        //then
        assertNotNull(findAnswer);
        assertThat(saveAnswer.getAnswerId(), is(findAnswer.getAnswerId()));
        assertThat(saveAnswer.getContent(), is(findAnswer.getContent()));
        assertThat(saveAnswer.getAnswerVote(), is(findAnswer.getAnswerVote()));
        assertThat(saveAnswer.isAccepted(), is(findAnswer.isAccepted()));
    }
    @Test
    void findAllAnswerTest() {
        //given
        Answer answer1 = new Answer("답변", false, 0);
        answer1.setAnswerId(1L);
        Answer answer2 = new Answer("답변임", true, 1);
        answer2.setAnswerId(2L);
        Answer answer3 = new Answer("답변임", true, 1);
        answer2.setAnswerId(3L);

        answerRepository.save(answer1);
        answerRepository.save(answer2);
//        answerRepository.save(answer3);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("answerId"));
        Page<Answer> page = new PageImpl<>(List.of(answer1, answer2), pageable, 2);
        //when
        Page<Answer> findPage = answerRepository.findAll(pageable);
        //then
        assertThat(findPage.getContent().size(), is(page.getContent().size()));
    }
    @Test
    void deleteAnswerTest() {
        //given
        Answer answer = new Answer("답변", false, 0);
        answer.setAnswerId(1L);

        answerRepository.save(answer);
        //when
        answerRepository.delete(answer);
        //then
        Optional<Answer> findAnswer = answerRepository.findById(answer.getAnswerId());

        assertThat(findAnswer, is(Optional.empty()));
    }
}
