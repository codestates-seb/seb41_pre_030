package Be_30.Project.vote.entity;

import Be_30.Project.answer.entity.Answer;
import Be_30.Project.audit.Auditable;
import Be_30.Project.member.entity.Member;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Setter
public class AnswerVote extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long AnswerVoteId;

    // TODO: member 매핑
     @ManyToOne
     @JoinColumn(name = "MEMBER-ID")
     private Member member;
    // TODO: Answer 매핑
     @ManyToOne
     @JoinColumn(name = "ANSWER-ID")
     private Answer answer;
}
