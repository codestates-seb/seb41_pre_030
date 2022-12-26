package Be_30.Project.answerTest.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import Be_30.Project.answer.controller.AnswerController;
import Be_30.Project.answer.dto.AnswerDto;
import Be_30.Project.answer.dto.AnswerDto.Patch;
import Be_30.Project.answer.dto.AnswerDto.Post;
import Be_30.Project.answer.dto.AnswerDto.Response;
import Be_30.Project.answer.entity.Answer;
import Be_30.Project.answer.mapper.AnswerMapper;
import Be_30.Project.answer.service.AnswerService;
import Be_30.Project.auth.jwt.JwtTokenizer;
import Be_30.Project.auth.utils.CustomAuthorityUtils;
import Be_30.Project.config.SecurityConfiguration;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Import({SecurityConfiguration.class,
        CustomAuthorityUtils.class,
        JwtTokenizer.class}
)
@WebMvcTest(AnswerController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class AnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private AnswerService answerService;

    @MockBean
    private AnswerMapper mapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void postAnswerTest() throws Exception {
        //given -> postDto
        AnswerDto.Post post = new Post("답변");
        String content = gson.toJson(post);

        given(mapper.answerPostDtoToAnswer(Mockito.any(AnswerDto.Post.class))).willReturn(new Answer());

        Answer answer = new Answer("답변", false, 0);
        answer.setAnswerId(1L);
        given(answerService.createAnswer(Mockito.any(Answer.class))).willReturn(answer);
        //when -> post
        ResultActions actions = mockMvc.perform(post("/answers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
        //then
        actions.andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("/answers/"))));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void patchAnswerTest() throws Exception {
        //given -> patchDto
        AnswerDto.Patch patch = new Patch(1L, "답변");
        String content = gson.toJson(patch);

        given(mapper.answerPatchDtoToAnswer(Mockito.any(AnswerDto.Patch.class))).willReturn(new Answer());

        Answer answer = new Answer("답변", false, 0);
        answer.setAnswerId(1L);
        given(answerService.updateAnswer(Mockito.any(Answer.class))).willReturn(answer);

        AnswerDto.Response response = new Response(1L, "답변", false, 0);
        given(mapper.answerToAnswerResponseDto(Mockito.any(Answer.class))).willReturn(response);
        //when
        ResultActions actions =
                mockMvc.perform(patch("/answers/{answer-id}", patch.getAnswerId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));
        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").value(answer.getContent()))
                .andExpect(jsonPath("$.data.accepted").value(answer.isAccepted()))
                .andExpect(jsonPath("$.data.votes").value(answer.getVotes()));
    }

    @Test
    void getAnswerTest() throws Exception {
        //given
        Answer answer = new Answer("답변", false, 0);
        answer.setAnswerId(1L);

        given(answerService.findAnswer(Mockito.anyLong())).willReturn(answer);

        AnswerDto.Response response = new Response(1L, "답변", false, 0);
        given(mapper.answerToAnswerResponseDto(Mockito.any(Answer.class))).willReturn(response);
        //when
        ResultActions actions =
                mockMvc.perform(get("/answers/{answer-id}", answer.getAnswerId())
                        .accept(MediaType.APPLICATION_JSON));
        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").value(answer.getContent()))
                .andExpect(jsonPath("$.data.accepted").value(answer.isAccepted()))
                .andExpect(jsonPath("$.data.votes").value(answer.getVotes()));
    }

    @Test
    void getAnswersTest() throws Exception {
        //given -> 객체 두개, 페이지네이션
        Answer answer1 = new Answer("답변", false, 0);
        answer1.setAnswerId(1L);
        Answer answer2 = new Answer("답변임", true, 1);
        answer2.setAnswerId(2L);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("answerId"));
        Page<Answer> answerPage =
                new PageImpl<>(List.of(answer1, answer2), pageable, 2);

        MultiValueMap<String, String> pages = new LinkedMultiValueMap<>();
        pages.add("page", "1");
        pages.add("size", "10");

        given(answerService.findAnswers(Mockito.anyInt(), Mockito.anyInt())).willReturn(answerPage);

        AnswerDto.Response response1 = new Response(1L, "답변", false, 0);
        AnswerDto.Response response2 = new Response(2L, "답변임", true, 1);
        List<AnswerDto.Response> responses = new ArrayList<>(List.of(response1, response2));
        given(mapper.answersToAnswerResponseDtos(Mockito.anyList())).willReturn(responses);
        //when
        ResultActions actions =
                mockMvc.perform(get("/answers")
                        .params(pages)
                        .accept(MediaType.APPLICATION_JSON));
        //then -> data 배열인지, 배열의 길이가 2인지 확인!!!!!!!
        MvcResult result = actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andReturn();

        List list = JsonPath.parse(result.getResponse().getContentAsString()).read("$.data");

        assertThat(list.size(), is(2));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteAnswerTest() throws Exception {
        //given
        Answer answer = new Answer("답변", false, 0);
        answer.setAnswerId(1L);

        doNothing().when(answerService).deleteAnswer(Mockito.anyLong());
        //when
        ResultActions actions =
                mockMvc.perform(delete("/answers/{answer-id}", answer.getAnswerId()));
        //then
        actions.andExpect(status().isNoContent());
    }

}
