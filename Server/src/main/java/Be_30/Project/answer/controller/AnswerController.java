package Be_30.Project.answer.controller;

import Be_30.Project.answer.dto.AnswerDto;
import Be_30.Project.answer.dto.AnswerDto.Response;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answers")
public class AnswerController {
    @PostMapping
    public ResponseEntity postAnswer() {
        return ResponseEntity.created(URI.create("/answers/1")).build();
    }
    @PatchMapping("/{answer-id}")
    public ResponseEntity patchAnswer() {
        AnswerDto.Response response =
            new Response(1L, "답변입니다.", 1, false);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{answer-id}")
    public ResponseEntity getAnswer() {
        AnswerDto.Response response =
            new Response(1L, "답변입니다.", 1, false);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity getAnswers() {
        AnswerDto.Response response =
            new Response(1L, "답변입니다.", 1, false);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{answer-id}")
    public ResponseEntity deleteAnswer() {
        return ResponseEntity.noContent().build();
    }

}
