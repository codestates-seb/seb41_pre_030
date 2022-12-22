package Be_30.Project.question.controller;

import Be_30.Project.question.dto.QuestionDto;
import Be_30.Project.question.entity.Question;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Test
    void getQuestionTest() throws Exception {
        // Given
        QuestionDto.Post post = new QuestionDto.Post("질문 제목", "질문 내용");
        String postContent = gson.toJson(post);

        ResultActions postActions = mockMvc.perform(
                post("/questions")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postContent)
        );

        MockHttpServletResponse response = postActions.andReturn().getResponse();
        System.out.println(response.getHeader("Location"));

        // When

        // Then
    }

    @Test
    void postQuestionTest() throws Exception {
        // Given
        QuestionDto.Post post = new QuestionDto.Post("질문 제목", "질문 내용");
        String postContent = gson.toJson(post);

        // When
        ResultActions actions = mockMvc.perform(
                post("/questions")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postContent)
        );

        // Then
        actions
                .andExpect(status().isCreated());
    }
}