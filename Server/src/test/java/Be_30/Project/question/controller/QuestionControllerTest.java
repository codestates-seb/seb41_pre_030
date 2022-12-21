package Be_30.Project.question.controller;

import Be_30.Project.question.dto.QuestionDto;
import Be_30.Project.question.entity.Question;
import Be_30.Project.question.mapper.QuestionMapperImpl;
import Be_30.Project.question.service.QuestionService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static Be_30.Project.util.ApiDocumentUtils.getRequestPreProcessor;
import static Be_30.Project.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(QuestionMapperImpl.class)
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(QuestionController.class)
@AutoConfigureRestDocs
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    @Autowired
    private Gson gson;

    @Test
    void getQuestionTest() throws Exception {
        // Given
        long questionId = 1L;
        Question question = createQuestion(questionId, "질문 제목", "질문 내용");
        given(questionService.findQuestion(questionId)).willReturn(question);

        // When
        ResultActions actions = mockMvc.perform(
                get("/questions/{questionId}", questionId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // Then
        actions
                .andExpect(status().isOk())
                .andDo(document(
                        "get-question",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("questionId").description("질문 번호")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("questionId").type(NUMBER).description("질문 번호"),
                                        fieldWithPath("subject").type(STRING).description("질문 제목"),
                                        fieldWithPath("content").type(STRING).description("질문 내용"),
                                        fieldWithPath("vote").type(NUMBER).description("추천/비추천 합"),
                                        fieldWithPath("view").type(NUMBER).description("조회수"),
                                        fieldWithPath("createdAt").type(STRING).description("생성 시각"),
                                        fieldWithPath("modifiedAt").type(STRING).description("수정 시각")
                                )
                        )
                ));
    }

    @Test
    void getQuestionsTest() throws Exception {
        // Given
        List<Question> questionList = List.of(
                createQuestion(1L, "질문 1 제목", "질문 1 내용"),
                createQuestion(2L, "질문 2 제목", "질문 2 내용"),
                createQuestion(3L, "질문 3 제목", "질문 3 내용")
        );
        given(questionService.findQuestionList()).willReturn(questionList);

        // When
        ResultActions actions = mockMvc.perform(
                get("/questions")
                        .accept(MediaType.APPLICATION_JSON)
        );

        // Then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(3))
                .andDo(document(
                        "get-questions",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        responseFields(
                                List.of(
                                        fieldWithPath("[].questionId").type(NUMBER).description("질문 번호"),
                                        fieldWithPath("[].subject").type(STRING).description("질문 제목"),
                                        fieldWithPath("[].content").type(STRING).description("질문 내용"),
                                        fieldWithPath("[].vote").type(NUMBER).description("추천/비추천 합"),
                                        fieldWithPath("[].view").type(NUMBER).description("조회수"),
                                        fieldWithPath("[].createdAt").type(STRING).description("생성 시각"),
                                        fieldWithPath("[].modifiedAt").type(STRING).description("수정 시각")
                                )
                        )
                ));

    }

    @Test
    void createQuestionTest() throws Exception {
        // Given
        QuestionDto.Post post = new QuestionDto.Post("질문 제목", "질문 내용");
        String content = gson.toJson(post);

        Question createdQuestion = createQuestion(1L, "질문 제목", "질문 내용");
        given(questionService.createQuestion(any(Question.class))).willReturn(createdQuestion);

        // When
        ResultActions actions = mockMvc.perform(
                post("/questions")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        // Then
        actions
                .andExpect(status().isOk())
                .andDo(document(
                        "create-question",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(List.of(
                                fieldWithPath("subject").type(STRING).description("질문 제목"),
                                fieldWithPath("content").type(STRING).description("질문 내용")
                        )),
                        responseFields(
                                List.of(
                                        fieldWithPath("questionId").type(NUMBER).description("질문 번호"),
                                        fieldWithPath("subject").type(STRING).description("질문 제목"),
                                        fieldWithPath("content").type(STRING).description("질문 내용"),
                                        fieldWithPath("vote").type(NUMBER).description("추천/비추천 합"),
                                        fieldWithPath("view").type(NUMBER).description("조회수"),
                                        fieldWithPath("createdAt").type(STRING).description("생성 시각"),
                                        fieldWithPath("modifiedAt").type(STRING).description("수정 시각")
                                )
                        )
                ));
    }

    @Test
    void updateQuestionTest() throws Exception {
        // Given
        long questionId = 1L;
        QuestionDto.Patch patch = new QuestionDto.Patch(questionId, "질문 제목", "질문 내용");
        String content = gson.toJson(patch);

        Question createdQuestion = createQuestion(questionId, "질문 제목", "질문 내용");
        given(questionService.updateQuestion(any(Question.class))).willReturn(createdQuestion);

        // When
        ResultActions actions = mockMvc.perform(
                patch("/questions/{questionId}", questionId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        // Then
        actions
                .andExpect(status().isOk())
                .andDo(document(
                        "patch-question",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(List.of(
                                fieldWithPath("questionId").type(NUMBER).description("질문 아이디"),
                                fieldWithPath("subject").type(STRING).description("질문 제목"),
                                fieldWithPath("content").type(STRING).description("질문 내용")
                        )),
                        responseFields(
                                List.of(
                                        fieldWithPath("questionId").type(NUMBER).description("질문 번호"),
                                        fieldWithPath("subject").type(STRING).description("질문 제목"),
                                        fieldWithPath("content").type(STRING).description("질문 내용"),
                                        fieldWithPath("vote").type(NUMBER).description("추천/비추천 합"),
                                        fieldWithPath("view").type(NUMBER).description("조회수"),
                                        fieldWithPath("createdAt").type(STRING).description("생성 시각"),
                                        fieldWithPath("modifiedAt").type(STRING).description("수정 시각")
                                )
                        )
                ));
    }

    @Test
    void deleteQuestionTest() throws Exception {
        // Given
        long questionId = 1L;
        willDoNothing().given(questionService).deleteQuestion(questionId);

        // When
        ResultActions actions = mockMvc.perform(
                delete("/questions/{questionId}", questionId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // Then
        actions
                .andExpect(status().isNoContent())
                .andDo(document(
                        "delete-question",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("questionId").description("질문 번호")
                        )
                ));
    }

    private Question createQuestion(long questionId, String subject, String content) {
        return Question.builder()
                .questionId(questionId)
                .subject(subject)
                .content(content)
                .vote(100)
                .view(12345).build();
    }
}