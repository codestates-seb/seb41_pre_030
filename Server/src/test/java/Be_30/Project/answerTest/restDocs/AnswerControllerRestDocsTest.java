//package Be_30.Project.answerTest.restDocs;
//
//import static Be_30.Project.util.ApiDocumentUtils.getRequestPreProcessor;
//import static Be_30.Project.util.ApiDocumentUtils.getResponsePreProcessor;
//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.startsWith;
//import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
//import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
//import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
//import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import Be_30.Project.answer.controller.AnswerController;
//import Be_30.Project.answer.dto.AnswerDto;
//import Be_30.Project.auth.jwt.JwtTokenizer;
//import Be_30.Project.auth.utils.CustomAuthorityUtils;
//import Be_30.Project.config.SecurityConfiguration;
//import com.google.gson.Gson;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.payload.JsonFieldType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//@Import({SecurityConfiguration.class,
//        CustomAuthorityUtils.class,
//        JwtTokenizer.class}
//)
//@WebMvcTest(AnswerController.class)
//@MockBean(JpaMetamodelMappingContext.class)
//@AutoConfigureRestDocs
//public class AnswerControllerRestDocsTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private Gson gson;
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    public void postAnswerTest() throws Exception {
//        //given -> postDto
//        AnswerDto.Post post = new AnswerDto.Post("답변입니다.");
//
//        String content = gson.toJson(post);
//
//        //when -> post 하고 uri 생성
//        ResultActions actions =
//                mockMvc.perform(post("/answers")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(content));
//
//        //then -> 생성된 uri, 기대값 비교, httpStatus = created
//        actions.andExpect(status().isCreated())
//                .andExpect(header().string("Location", is(startsWith("/answers/"))))
//                .andDo(document(
//                        "post-answer",
//                        getRequestPreProcessor(),
//                        requestFields(
//                                List.of(
//                                        fieldWithPath("content").type(JsonFieldType.STRING).description("답변 내용"),
//                                        fieldWithPath("accepted").type(JsonFieldType.BOOLEAN).description("채택 여부")
//                                )
//                        ),
//                        responseHeaders(
//                                headerWithName(HttpHeaders.LOCATION).description("Location Header : 등록된 리소스의 URI")
//                        )
//
//                ));
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    public void patchAnswerTest() throws Exception {
//        //given -> patchDto
//        AnswerDto.Patch patch = new AnswerDto.Patch(1L, "답변입니다.");
//
//        String content = gson.toJson(patch);
//        //when -> patch
//        ResultActions actions =
//                mockMvc.perform(patch("/answers/1")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(content));
//        //then -> 변경된 값과 같은지
//        actions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.content").value(patch.getContent()))
//                .andDo(document("patch-answer",
//                        getRequestPreProcessor(),
//                        getResponsePreProcessor(),
//                        requestFields(
//                                List.of(
//                                        fieldWithPath("content").type(JsonFieldType.STRING).description("답변 내용"),
//                                        fieldWithPath("accepted").type(JsonFieldType.BOOLEAN).description("채택 여부")
//                                )
//                        ),
//                        responseFields(
//                                List.of(
//                                        fieldWithPath("answerId").type(JsonFieldType.NUMBER).description("답변 식별자"),
//                                        fieldWithPath("content").type(JsonFieldType.STRING).description("답변 내용"),
//                                        fieldWithPath("accepted").type(JsonFieldType.BOOLEAN).description("채택 여부"),
//                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성 시간"),
//                                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("수정 시간")
//                                )
//                        )
//                ))
//                .andReturn();
//    }
//
//    @Test
//    public void getAnswerTest() throws Exception {
//        //given -> answer 객체?
//        AnswerDto.Post post = new AnswerDto.Post("답변입니다.");
//
//        String content = gson.toJson(post);
//        //when -> get
//        ResultActions actions =
//                mockMvc.perform(get("/answers/1")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(content));
//        //then-> 불러와진값과 비교
//        actions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.content").value(post.getContent()))
//                .andDo(document("get-answer",
//                        getResponsePreProcessor(),
//                        responseFields(
//                                List.of(
//                                        fieldWithPath("answerId").type(JsonFieldType.NUMBER).description("답변 식별자"),
//                                        fieldWithPath("content").type(JsonFieldType.STRING).description("답변 내용"),
//                                        fieldWithPath("accepted").type(JsonFieldType.BOOLEAN).description("채택 여부"),
//                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성 시간"),
//                                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("수정 시간")
//                                )
//                        )))
//                .andReturn();
//    }
//
//    @Test
//    public void getAnswersTest() throws Exception {
//        //given -> post 2개
//        AnswerDto.Post post1 = new AnswerDto.Post("답변입니다.");
//        AnswerDto.Post post2 = new AnswerDto.Post("답변");
//        //when -> get
//        ResultActions actions =
//                mockMvc.perform(get("/answers")
//                        .accept(MediaType.APPLICATION_JSON));
//        //then -> 배열인지?
//        actions.andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andDo(document("get-answers",
//                        getResponsePreProcessor(),
//                        responseFields(
//                                Arrays.asList(
//                                        fieldWithPath("[]").type(JsonFieldType.ARRAY).description("답변 목록"),
//                                        fieldWithPath("[].answerId").type(JsonFieldType.NUMBER).description("답변 식별자"),
//                                        fieldWithPath("[].content").type(JsonFieldType.STRING).description("답변 내용"),
//                                        fieldWithPath("[].accepted").type(JsonFieldType.BOOLEAN).description("채택 여부"),
//                                        fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("생성 시간"),
//                                        fieldWithPath("[].modifiedAt").type(JsonFieldType.STRING).description("수정 시간")
//                                )
//                        )
//                ));
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    public void deleteAnswerTest() throws Exception {
//        //given
//        AnswerDto.Post post = new AnswerDto.Post("답변입니다.");
//        //when
//        ResultActions actions =
//                mockMvc.perform(delete("/answers/1")
//                        .accept(MediaType.APPLICATION_JSON));
//        //then
//        actions.andExpect(status().isNoContent())
//                .andDo(document("delete-answer"));
//    }
//}
