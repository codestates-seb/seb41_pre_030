package Be_30.Project.vote.entity;

import Be_30.Project.answer.entity.Answer;
import Be_30.Project.audit.Auditable;
import Be_30.Project.member.entity.Member;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Setter
@NoArgsConstructor
public class AnswerVote extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long AnswerVoteId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private VoteStatus voteStatus;

    // TODO: member 매핑
     @ManyToOne
     @JoinColumn(name = "MEMBER_ID")
     private Member member;
    // TODO: Answer 매핑
     @ManyToOne
     @JoinColumn(name = "ANSWER_ID")
     private Answer answer;

     public enum VoteStatus {
         VOTE_UP("추천"),
         VOTE_DOWN("비추천");
         private String status;

         VoteStatus(String status) {
             this.status = status;
         }
     }
}
