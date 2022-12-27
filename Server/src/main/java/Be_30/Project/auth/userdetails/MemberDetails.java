package Be_30.Project.auth.userdetails;

import Be_30.Project.auth.utils.CustomAuthorityUtils;
import Be_30.Project.member.entity.Member;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class MemberDetails extends Member implements UserDetails {
    private final CustomAuthorityUtils authorityUtils;


    MemberDetails(Member member, CustomAuthorityUtils authorityUtils) {
        setMemberId(member.getMemberId());
        setEmail(member.getEmail());
        setPassword(member.getPassword());
        setRoles(member.getRoles());
        this.authorityUtils = authorityUtils;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityUtils.createAuthorities(this.getRoles());
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
