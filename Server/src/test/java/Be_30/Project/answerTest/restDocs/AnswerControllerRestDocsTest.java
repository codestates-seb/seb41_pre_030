package Be_30.Project.answerTest.restDocs;

import Be_30.Project.answer.entity.Answer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(Answer.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class AnswerControllerRestDocsTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void postAnswerTest() {
        //given
        //when
        //then
    }
    @Test
    public void patchAnswerTest() {
        //given
        //when
        //then
    }
    @Test
    public void getAnswerTest() {
        //given
        //when
        //then
    }
    @Test
    public void getAnswersTest() {
        //given
        //when
        //then
    }
    @Test
    public void deleteAnswerTest() {
        //given
        //when
        //then
    }
}
