package Be_30.Project.auth.jwt.refreshtoken.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "REFRESH_TOKEN_ID")
    private long refreshTokenId;

    @Column(name = "TOKEN_VALUE")
    private String tokenValue;

    @Column(name = "TOKEN_EMAIL")
    private String tokenEmail;

    @Column(name = "MEMBER_ID")
    private long memberId;

    public RefreshToken(String tokenValue, String tokenEmail, long tokenId) {
        this.tokenValue = tokenValue;
        this.tokenEmail = tokenEmail;
        this.memberId = tokenId;
    }
}
