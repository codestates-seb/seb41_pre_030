package Be_30.Project.auth.oauth;

import Be_30.Project.member.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.*;
import java.util.Map;

@Getter @Setter
@Entity
public class CustomOauthMember { // 회원가입 대기 임시 데이터 (회원가입 완료와 함께 삭제)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Member.OauthPlatform oauthPlatform;

    private String profileImageSrc;

    public CustomOauthMember() {
    }

    public CustomOauthMember(OAuth2User oAuth2User, Member.OauthPlatform platform) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        if (platform.equals(Member.OauthPlatform.GOOGLE)) {
            profileImageSrc = String.valueOf(attributes.get("picture"));
        } else {
            profileImageSrc = String.valueOf(attributes.get("avatar_url"));
        }
        this.oauthPlatform = platform;
        this.email = String.valueOf(attributes.get("email"));
        this.nickname = String.valueOf(attributes.get("name"));
    }
}
