package Be_30.Project.question.entity;

import Be_30.Project.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Question extends Auditable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long questionId;

    private String subject;

    private String content;

    private int vote;

    private int view;

    public void updateQuestion(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

//    private User user;
}
