package Be_30.Project.answerTest.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import Be_30.Project.answer.dto.AnswerDto;
import Be_30.Project.answer.dto.AnswerDto.Patch;
import Be_30.Project.answer.entity.Answer;
import Be_30.Project.answer.repository.AnswerRepository;
import Be_30.Project.answer.service.AnswerService;
import Be_30.Project.exception.BusinessLogicException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AnswerServiceTest {
    @Mock
    private AnswerRepository answerRepository;

    @InjectMocks
    private AnswerService answerService;

    @Test
    void findVerifiedAnswerTest() {
        //given -> answerId를 가진 객체? / 레포 findById
        Answer answer = new Answer("답", false, 1);
        answer.setAnswerId(1L);

        given(answerRepository.findById(Mockito.anyLong())).willReturn(Optional.empty());
        //when
        //then -> 존재하지 않으면 예외 던지기
        assertThrows(BusinessLogicException.class, () -> answerService.findVerifiedAnswer(answer.getAnswerId()));
    }

    @Test
    void updateAnswerTest() {
        //given -> 객체, patchDto
        Answer answer = new Answer("답", false, 1);
        answer.setAnswerId(1L);

        AnswerDto.Patch patch = new Patch(1L, "답변");
        //when -> 변경 내용이 null 아니라면 변경하는 로직을 테스트?
        Optional.ofNullable(patch.getContent())
            .ifPresent(content -> answer.setContent(content));
        //then
        assertThat(answer.getContent(), is(patch.getContent()));
        assertThat(answer.getAnswerId(), is(patch.getAnswerId()));
    }
}
