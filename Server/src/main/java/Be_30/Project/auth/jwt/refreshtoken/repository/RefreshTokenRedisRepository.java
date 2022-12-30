package Be_30.Project.auth.jwt.refreshtoken.repository;

import Be_30.Project.auth.jwt.refreshtoken.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public interface RefreshTokenRedisRepository extends JpaRepository<RefreshToken,Long> {

    private final RedisTemplate<String, String> redisTemplate;

    private final RedisTemplate<String, String> redisBlackListTemplate;

}
