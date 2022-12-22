package Be_30.Project.answer.entity;

import Be_30.Project.audit.Auditable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Answer extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;
    private String content;
    private int vote;
    private boolean accepted;
    private int votes;

    public Answer(String content, boolean accepted, int votes) {
        this.content = content;
        this.accepted = accepted;
        this.votes = votes;
    }
}
