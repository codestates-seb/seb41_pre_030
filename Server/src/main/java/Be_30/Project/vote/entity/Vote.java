package Be_30.Project.vote.entity;

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
public class Vote extends Auditable {
    @Id
    private Long voteId;
    private int voteCount;
}
