package Be_30.Project.auth.jwt.refreshtoken.repository;

import Be_30.Project.auth.jwt.refreshtoken.entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByTokenValue(String tokenValue);

    void deleteRefreshTokenByTokenValue(String tokenValue);

    Optional<RefreshToken> findRefreshTokenByTokenValue(String refreshToken);

}
