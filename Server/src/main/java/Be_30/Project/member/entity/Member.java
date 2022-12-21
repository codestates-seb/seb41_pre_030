package Be_30.Project.member.entity;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity //확인하기
public class Member {
    @Id
    private long memberId;

    private String email;

    private String nickName;

    private String password;

    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime modifiedAt = LocalDateTime.now();

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
}