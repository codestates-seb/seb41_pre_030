package Be_30.Project.auth.oauth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OauthMemberRepository extends JpaRepository<CustomOauthMember, Long> {

    Optional<CustomOauthMember> findByEmail(String email);
}
