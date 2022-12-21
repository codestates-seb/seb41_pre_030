package Be_30.Project.answer.entity;

import Be_30.Project.audit.Auditable;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Answer extends Auditable {
    @Id
    private Long answerId;
    private String content;
    private boolean accepted;
    private int answerVote;

}
