package Be_30.Project.member.entity;

import Be_30.Project.answer.entity.Answer;
import Be_30.Project.audit.Auditable;
import Be_30.Project.question.entity.Question;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import Be_30.Project.file.entity.ImageFile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member extends Auditable {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long memberId;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String confirmedPassword;

    @Column(length = 100, nullable = false)
    private String nickName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private ImageFile profileImage;

    //user 권한 정보와 관련된 별도의 엔티티 생성 필요 없음
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    @OneToMany(mappedBy = "member")
    @Column
    List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @Column
    List<Answer> answers= new ArrayList<>();

    public enum MemberStatus {
        MEMBER_ACTIVE("활동중"),
        MEMBER_SLEEP("휴면 상태"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }

    public void addQuestion(Question question){
        questions.add(question);
    }

    public void addAnswer(Answer answer){
        answers.add(answer);
    }

    public void removeQuestion(Question question){questions.remove(question);}

    public void removeQuestion(Answer answer){answers.remove(answer);}
}