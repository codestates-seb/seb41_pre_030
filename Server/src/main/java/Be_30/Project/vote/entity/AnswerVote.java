package Be_30.Project.vote.entity;

import Be_30.Project.audit.Auditable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class AnswerVote extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long AnswerVoteId;

    // TODO: member 매핑
    // @ManyToOne
    // @JoinColumn(name = "MEMBER-ID")
    // private Member member;
    // TODO: Answer 매핑
    // @ManyToOne
    // @JoinColumn(name = "ANSWER-ID")
    // private Answer answer;
}
