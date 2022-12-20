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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;


    @Test
    public void postUserTest() throws Exception {
        MemberDto.Post memberPostDto = new MemberDto.Post("egg30@gmail.com", "달걀한판"
        ,"1111",MemberStatus.MEMBER_ACTIVE);

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
                .andExpect(header().string("Location", is(startsWith("/members/"))));
    }


    @Test
    void patchMemberTest() throws  Exception{
        MemberDto.Patch memberPatchDto = new MemberDto.Patch(1,"heebum@gmail.com",
            "희범", "1234", MemberStatus.MEMBER_ACTIVE);

        String content = gson.toJson(memberPatchDto);

        ResultActions actions =
            mockMvc.perform(
                patch("/members/1")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            );

        actions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value(memberPatchDto.getEmail()))
            .andExpect(jsonPath("$.nickName").value(memberPatchDto.getNickName()))
            .andExpect(jsonPath("$.password").value(memberPatchDto.getPassword()));

    }

    @Test
    void getMemberTest() throws Exception {
        // given: MemberController의 getMember()를 테스트하기 위해서 postMember()를 이용해 테스트 데이터를 생성 후, DB에 저장
        MemberDto.Response response = new MemberDto.Response(1,"heebum@gmail.com",
            "희범", "1234", MemberStatus.MEMBER_ACTIVE);
        String content = gson.toJson(response);

        ResultActions actions =
            mockMvc.perform(
                get("/members/1")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            );

        actions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value(response.getEmail()))
            .andExpect(jsonPath("$.nickName").value(response.getNickName()))
            .andExpect(jsonPath("$.password").value(response.getPassword()));
    }

    @Test
    void deleteMemberTest() throws Exception{

        ResultActions actions =
            mockMvc.perform(
                delete("/members/1")
            );


        actions
                .andExpect(status().isNoContent());
    }

    @Test
    void getMembersTest() throws Exception{

        ResultActions actions =
            mockMvc.perform(
                get("/members")
                    .accept(MediaType.APPLICATION_JSON)
            );


        MvcResult result = actions
                                    .andExpect(status().isOk())
                                    .andExpect(jsonPath("$").isArray())
                                    .andReturn();
        List list = JsonPath.parse(result.getResponse().getContentAsString()).read("$");

        assertThat(list.size(), is(2));
    }

}
