package Be_30.Project.member.repository;

import Be_30.Project.member.entity.Member;
import Be_30.Project.member.entity.Member.MemberStatus;
import Be_30.Project.question.entity.Question;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByMemberIdAndMemberStatus(Long memberId,MemberStatus memberStatus);
    Page<Member> findAllByMemberStatus(Pageable pageable, MemberStatus memberStatus);

}
