package Be_30.Project.auth.userdetails;

import Be_30.Project.auth.utils.CustomAuthorityUtils;
import Be_30.Project.member.entity.Member;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class MemberDetails implements UserDetails {

    private CustomAuthorityUtils authorityUtils;
    private long memberId;
    private String email;
    private String password;
    private List<String> roles;
    private LocalDateTime lastLogin;

    public MemberDetails(CustomAuthorityUtils authorityUtils, Member member) {
        this.authorityUtils = authorityUtils;
        this.memberId = member.getMemberId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.roles = member.getRoles();
        this.lastLogin = member.getLastLogin();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityUtils.createAuthorities(roles);
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
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
