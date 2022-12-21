package Be_30.Project.controller;


import static Be_30.Project.util.ApiDocumentUtils.getRequestPreProcessor;
import static Be_30.Project.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import Be_30.Project.member.controller.MemberController;
import Be_30.Project.member.dto.MemberDto;
import Be_30.Project.member.entity.Member.MemberStatus;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;


@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Test
    public void postUserTest() throws Exception {
        MemberDto.Post memberPostDto = new MemberDto.Post("egg30@gmail.com", "달걀한판","1111");

        String content = gson.toJson(memberPostDto);

        ResultActions actions =
            mockMvc.perform(
                post("/members")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            );

        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("/members/"))))
                .andDo(document("post-member",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestFields(
                    List.of(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("닉네임"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                    )
                ),
                responseHeaders(
                    headerWithName(HttpHeaders.LOCATION).description("Location header. 등록된 리소스의 URI")
                )
            ));
    }


    @Test
    void patchMemberTest() throws  Exception{
        MemberDto.Patch memberPatchDto = new MemberDto.Patch(1,"heebum@gmail.com",
            "희범",MemberStatus.MEMBER_ACTIVE);

        String content = gson.toJson(memberPatchDto);

        ResultActions actions =
            mockMvc.perform(
                patch("/members/{member-id}",1)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            );

        actions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value(memberPatchDto.getEmail()))
            .andExpect(jsonPath("$.nickName").value(memberPatchDto.getNickName()))
            .andDo(document("patch-member",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                pathParameters(
                    parameterWithName("member-id").description("회원 식별자")
                ),
                requestFields(
                    List.of(
                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자").ignored(),
                        fieldWithPath("email").type(JsonFieldType.STRING).description("이름").optional(),
                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("휴대폰 번호").optional(),
                        fieldWithPath("memberStatus").type(JsonFieldType.STRING).description("회원 상태: MEMBER_ACTIVE / MEMBER_SLEEP / MEMBER_QUIT").optional()
                    )
                ),
                responseFields(
                    List.of(
                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("이름"),
                        fieldWithPath("memberStatus").type(JsonFieldType.STRING).description("회원 상태: 활동중 / 휴면 상태 / 탈퇴 상태")
                    )
                )
            ));

    }

    @Test
    void getMemberTest() throws Exception {

        MemberDto.Response response = new MemberDto.Response(1,"heebum@gmail.com",
            "희범",MemberStatus.MEMBER_ACTIVE);
        String content = gson.toJson(response);

        ResultActions actions =
            mockMvc.perform(
                get("/members/{member-id}",1)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            );

        actions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value(response.getEmail()))
            .andExpect(jsonPath("$.nickName").value(response.getNickName()))
            .andDo(document("get-member", getRequestPreProcessor(), getResponsePreProcessor(),
                pathParameters(parameterWithName("member-id").description("회원 식별자"))
                ,responseFields(
                    List.of(
                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("이름"),
                        fieldWithPath("memberStatus").type(JsonFieldType.STRING).description("회원 상태: 활동중 / 휴면 상태 / 탈퇴 상태")
                        )))
            );
    }

    @Test
    void deleteMemberTest() throws Exception{

        ResultActions actions =
            mockMvc.perform(
                delete("/members/1")
            );


        actions
                .andExpect(status().isNoContent())
                .andDo(document("delete-member"
            ));

    }

    @Test
    void getMembersTest() throws Exception{

        ResultActions actions =
            mockMvc.perform(
                get("/members")
                    .accept(MediaType.APPLICATION_JSON)
            );


        actions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andDo(document("get-members",getResponsePreProcessor(),
                responseFields(List.of(
                    fieldWithPath("[]").type(JsonFieldType.ARRAY).description("결과 데이터"),
                    fieldWithPath("[].memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                    fieldWithPath("[].email").type(JsonFieldType.STRING).description("이메일"),
                    fieldWithPath("[].nickName").type(JsonFieldType.STRING).description("닉네임"),
                    fieldWithPath("[].memberStatus").type(JsonFieldType.STRING).description("회원 상태: 활동중 / 휴면 상태 / 탈퇴 상태")
                )))



            );


    }

}
