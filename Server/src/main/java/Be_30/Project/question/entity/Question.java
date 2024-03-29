package Be_30.Project.question.entity;

import Be_30.Project.answer.entity.Answer;
import Be_30.Project.audit.Auditable;
import Be_30.Project.member.entity.Member;
import Be_30.Project.vote.entity.QuestionVote;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    private int votes;

    private int views;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "question")
    private List<QuestionVote> questionVotes = new ArrayList<>();

    public int getAnswerCount() {
        return answers.size();
    }

    public void setMember(Member member) {
        this.member = member;
    }

    // 질문 수정 (제목, 본문)
    public void updateQuestion(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

    // 투표 + 1
    public void makeUpVote() {
        this.votes++;
    }

    // 투표 - 1
    public void makeDownVote() {
        this.votes--;
    }

    // 조회수 + 1
    public void viewQuestion() {
        this.views++;
    }
}
