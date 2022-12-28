package Be_30.Project.vote.entity;

import Be_30.Project.audit.Auditable;
import Be_30.Project.member.entity.Member;
import Be_30.Project.question.entity.Question;
import Be_30.Project.vote.entity.AnswerVote.VoteStatus;
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
import lombok.Setter;

@Getter
@Entity
@Setter
public class QuestionVote extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long QuestionVoteId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private VoteStatus voteStatus;


    // TODO: member 매핑
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    // TODO: Question 매핑
    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

    public enum VoteStatus {
        VOTE_UP("추천"),
        VOTE_DOWN("비추천"),
        VOTE_CANCEL("취소");
        private String status;

        VoteStatus(String status) {
            this.status = status;
        }
    }
}
