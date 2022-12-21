package Be_30.Project.question;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Entity
public class Question {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long questionId;

    private String subject;

    private String content;

    private int vote;

    private int view;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

//    private User user;
}
