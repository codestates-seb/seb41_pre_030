package Be_30.Project.member.repository;

import Be_30.Project.member.entity.Member;
import Be_30.Project.question.entity.Question;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByEmailAndOauthPlatform(String email, Member.OauthPlatform oauthPlatform);

}
