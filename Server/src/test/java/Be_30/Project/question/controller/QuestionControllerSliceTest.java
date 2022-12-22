package Be_30.Project.question.controller;

import Be_30.Project.answer.entity.Answer;
import Be_30.Project.member.entity.Member;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static Be_30.Project.util.ApiDocumentUtils.getRequestPreProcessor;
import static Be_30.Project.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(QuestionMapperImpl.class)
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(QuestionController.class)
@AutoConfigureRestDocs
class QuestionControllerSliceTest {

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
        Member member = createMember(1L, "닉네임", "email@com");
        Question question = createQuestion(questionId, "질문 제목", "질문 내용");
        Answer answer1 = createAnswer(1L, "답변 내용_1", true);
        Answer answer2 = createAnswer(2L, "답변 내용_2", false);

        question.getAnswers().add(answer1); // 질문 내에 답변 추가
        question.getAnswers().add(answer2); // 질문 내에 답변 추가
        question.setMember(member); // 질문과 회원 연결
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
                                        fieldWithPath("data.questionId").type(NUMBER).description("질문 번호"),
                                        fieldWithPath("data.subject").type(STRING).description("질문 제목"),
                                        fieldWithPath("data.content").type(STRING).description("질문 내용"),
                                        fieldWithPath("data.votes").type(NUMBER).description("추천/비추천 합"),
                                        fieldWithPath("data.views").type(NUMBER).description("조회수"),
                                        fieldWithPath("data.createdAt").type(STRING).description("생성 시각"),
                                        fieldWithPath("data.modifiedAt").type(STRING).description("수정 시각"),

                                        fieldWithPath("data.member").type(OBJECT).description("질문 작성자"),
                                        fieldWithPath("data.member.memberId").type(NUMBER).description("질문 작성자 ID"),
                                        fieldWithPath("data.member.email").type(STRING).description("질문 작성자 이메일"),
                                        fieldWithPath("data.member.nickName").type(STRING).description("질문 작성자 닉네임"),
                                        fieldWithPath("data.member.memberStatus").type(STRING).description(STRING).description("질문 작성자 상태"),

                                        fieldWithPath("data.answers[]").type(ARRAY).description("질문에 속한 답변 목록"),
                                        fieldWithPath("data.answers[].answerId").type(NUMBER).description("답변 ID"),
                                        fieldWithPath("data.answers[].content").type(STRING).description("답변 내용"),
                                        fieldWithPath("data.answers[].accepted").type(BOOLEAN).description("답변 채택 유무"),
                                        fieldWithPath("data.answers[].votes").type(NUMBER).description("답변 투표 수")
                                )
                        )
                ));
    }

    @Test
    void getQuestionsTest() throws Exception {
        // Given
        String searchValue = "검색 키워드";
        String tab = "newest";
        int page = 1, size = 10;

        List<Question> questionList = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Member member = createMember(i, "닉네임_" + i, "email_" + i + "@com");
            Question question = createQuestion(i, "제목_" + i, "내옹_" + i);
            Answer answer = createAnswer(i, "답변_" + i, false);
            question.setMember(member);
            question.getAnswers().add(answer);
            questionList.add(question);
        }

        Page<Question> questionPage = new PageImpl<>(
                questionList,
                PageRequest.of(page - 1, size, Sort.by("questionId").descending()),
                2
        );
        given(questionService.findQuestions(anyInt(), anyInt(), anyString(), anyString())).willReturn(questionPage);

        // When
        ResultActions actions = mockMvc.perform(
                get("/questions")
                        .param("page", Integer.toString(page))
                        .param("size", Integer.toString(size))
                        .param("q", searchValue)
                        .param("tab", tab)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // Then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.size()").value(2))
                .andDo(document(
                        "get-questions",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestParameters(List.of(
                                parameterWithName("page").description("페이지"),
                                parameterWithName("size").description("페이지 당 데이터 개수"),
                                parameterWithName("q").description("검색 키워드"),
                                parameterWithName("tab").description("검색 태그 (newest/votes/active)")
                        )),
                        responseFields(
                                List.of(
                                        fieldWithPath("data[].questionId").type(NUMBER).description("질문 번호"),
                                        fieldWithPath("data[].subject").type(STRING).description("질문 제목"),
                                        fieldWithPath("data[].content").type(STRING).description("질문 내용"),
                                        fieldWithPath("data[].votes").type(NUMBER).description("추천/비추천 합"),
                                        fieldWithPath("data[].views").type(NUMBER).description("조회수"),
                                        fieldWithPath("data[].createdAt").type(STRING).description("생성 시각"),
                                        fieldWithPath("data[].modifiedAt").type(STRING).description("수정 시각"),

                                        fieldWithPath("data[].member").type(OBJECT).description("질문 작성자"),
                                        fieldWithPath("data[].member.memberId").type(NUMBER).description("질문 작성자 ID"),
                                        fieldWithPath("data[].member.email").type(STRING).description("질문 작성자 이메일"),
                                        fieldWithPath("data[].member.nickName").type(STRING).description("질문 작성자 닉네임"),
                                        fieldWithPath("data[].member.memberStatus").type(STRING).description(STRING).description("질문 작성자 상태"),

                                        fieldWithPath("data[].answers[]").type(ARRAY).description("질문에 속한 답변 목록"),
                                        fieldWithPath("data[].answers[].answerId").type(NUMBER).description("답변 ID"),
                                        fieldWithPath("data[].answers[].content").type(STRING).description("답변 내용"),
                                        fieldWithPath("data[].answers[].accepted").type(BOOLEAN).description("답변 채택 유무"),
                                        fieldWithPath("data[].answers[].votes").type(NUMBER).description("답변 투표 수"),

                                        fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현재 페이지 번호"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("각 페이지당 질문 수"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 질문 갯수"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 갯수")
                                )
                        )
                ));

    }

    @Test
    void createQuestionTest() throws Exception {
        // Given
        QuestionDto.Post post = new QuestionDto.Post("질문 제목", "질문 내용");
        String content = gson.toJson(post);

        Member member = createMember(1L, "닉네임", "email@com");
        Question createdQuestion = createQuestion(1L, "질문 제목", "질문 내용");
        createdQuestion.setMember(member);
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
                .andExpect(status().isCreated())
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
                                        fieldWithPath("data.questionId").type(NUMBER).description("질문 번호"),
                                        fieldWithPath("data.subject").type(STRING).description("질문 제목"),
                                        fieldWithPath("data.content").type(STRING).description("질문 내용"),
                                        fieldWithPath("data.votes").type(NUMBER).description("추천/비추천 합"),
                                        fieldWithPath("data.views").type(NUMBER).description("조회수"),
                                        fieldWithPath("data.createdAt").type(STRING).description("생성 시각"),
                                        fieldWithPath("data.modifiedAt").type(STRING).description("수정 시각"),

                                        fieldWithPath("data.member").type(OBJECT).description("질문 작성자"),
                                        fieldWithPath("data.member.memberId").type(NUMBER).description("질문 작성자 ID"),
                                        fieldWithPath("data.member.email").type(STRING).description("질문 작성자 이메일"),
                                        fieldWithPath("data.member.nickName").type(STRING).description("질문 작성자 닉네임"),
                                        fieldWithPath("data.member.memberStatus").type(STRING).description(STRING).description("질문 작성자 상태"),

                                        fieldWithPath("data.answers[]").type(ARRAY).description("질문에 속한 답변 목록")
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

        Answer answer = createAnswer(1L, "답변 내용", true);
        Member member = createMember(1L, "닉네임", "email@com");
        Question createdQuestion = createQuestion(questionId, "질문 제목", "질문 내용");
        createdQuestion.getAnswers().add(answer);
        createdQuestion.setMember(member);
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
                                        fieldWithPath("data.questionId").type(NUMBER).description("질문 번호"),
                                        fieldWithPath("data.subject").type(STRING).description("질문 제목"),
                                        fieldWithPath("data.content").type(STRING).description("질문 내용"),
                                        fieldWithPath("data.votes").type(NUMBER).description("추천/비추천 합"),
                                        fieldWithPath("data.views").type(NUMBER).description("조회수"),
                                        fieldWithPath("data.createdAt").type(STRING).description("생성 시각"),
                                        fieldWithPath("data.modifiedAt").type(STRING).description("수정 시각"),

                                        fieldWithPath("data.member").type(OBJECT).description("질문 작성자"),
                                        fieldWithPath("data.member.memberId").type(NUMBER).description("질문 작성자 ID"),
                                        fieldWithPath("data.member.email").type(STRING).description("질문 작성자 이메일"),
                                        fieldWithPath("data.member.nickName").type(STRING).description("질문 작성자 닉네임"),
                                        fieldWithPath("data.member.memberStatus").type(STRING).description(STRING).description("질문 작성자 상태"),

                                        fieldWithPath("data.answers[]").type(ARRAY).description("질문에 속한 답변 목록"),
                                        fieldWithPath("data.answers[].answerId").type(NUMBER).description("답변 ID"),
                                        fieldWithPath("data.answers[].content").type(STRING).description("답변 내용"),
                                        fieldWithPath("data.answers[].accepted").type(BOOLEAN).description("답변 채택 유무"),
                                        fieldWithPath("data.answers[].votes").type(NUMBER).description("답변 투표 수")
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
                .votes(100)
                .views(12345)
                .answers(new ArrayList<>()).build();
    }

    private Member createMember(long memberId, String nickname, String email) {
        Member member = new Member();
        member.setMemberId(memberId);
        member.setNickName(nickname);
        member.setEmail(email);
        return member;
    }

    private Answer createAnswer(long answerId, String content, boolean accepted) {
        Answer answer = new Answer();
        answer.setAnswerId(answerId);
        answer.setContent(content);
        answer.setAccepted(accepted);
        answer.setVotes(30);
        return answer;
    }
}