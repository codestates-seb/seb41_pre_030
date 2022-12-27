package Be_30.Project.answer.entity;

import Be_30.Project.audit.Auditable;
import Be_30.Project.member.entity.Member;
import Be_30.Project.question.entity.Question;
import Be_30.Project.vote.entity.AnswerVote;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    @Lob
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private boolean adopt;
    @Column(nullable = false)
    private int votes;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

    public void addMember(Member member) {
        this.member = member;
    }

    public void addQuestion(Question question) {
        this.question = question;
    }

    @OneToMany(mappedBy = "answer")
    private List<AnswerVote> answerVotes = new ArrayList<>();

    public Answer(String content, boolean adopt, int votes) {
        this.content = content;
        this.adopt = adopt;
        this.votes = votes;
    }
}
