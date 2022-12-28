package Be_30.Project.voteTest.answerVote;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import Be_30.Project.answer.entity.Answer;
import Be_30.Project.auth.filter.JwtAuthenticationFilter;
import Be_30.Project.member.entity.Member;
import Be_30.Project.vote.controller.AnswerVoteController;
import Be_30.Project.vote.dto.AnswerVoteResponseDto;
import Be_30.Project.vote.entity.AnswerVote;
import Be_30.Project.vote.mapper.AnswerVoteMapper;
import Be_30.Project.vote.service.AnswerVoteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@WebMvcTest(AnswerVoteController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class AnswerVoteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AnswerVoteService answerVoteService;

    @MockBean
    private AnswerVoteMapper mapper;

    @Test
    void postVoteUp() throws Exception {
        //given
        Answer answer = new Answer("답", false, 0);
        answer.setAnswerId(1L);

        Member member = new Member();
        member.setEmail("a@gmail.com");
        member.setNickName("에이");
        member.setPassword("1111");
        member.setMemberId(1L);

        AnswerVote answerVote = new AnswerVote();
        answerVote.setAnswer(answer);
        answerVote.setMember(member);
        answerVote.setAnswerVoteId(1L);

        given(answerVoteService.addVoteUp(Mockito.anyLong(), Mockito.anyLong())).willReturn(answerVote);

        AnswerVoteResponseDto response = AnswerVoteResponseDto.builder()
            .answerVoteId(1L)
            .votes(1)
            .build();

        given(mapper.AnswerVoteToAnswerVoteResponseDto(Mockito.any(AnswerVote.class))).willReturn(
            response);

        //when
        ResultActions actions =
            mockMvc.perform(post("/answers/{answer-id}/vote-up", answer.getAnswerId()));
        //then
        actions.andExpect(status().isOk())
            .andExpect(jsonPath("$.data.answerVoteId").value(answerVote.getAnswerVoteId()))
            .andExpect(jsonPath("$.data.votes").value(answerVote.getAnswer().getVotes()));
    }
}
