package Be_30.Project.auth.jwt.refreshtoken.repository;

import Be_30.Project.auth.jwt.JwtTokenizer;
import io.jsonwebtoken.Claims;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class RedisRepository {
    //REFRESHTOKEN
    //KEY : memberId VAL : token
    @Autowired
    private final RedisTemplate<String, String> redisTemplate;
    //ACCESSTOKEN
    @Autowired
    private final RedisTemplate<String, String> redisBlackListTemplate;

    private final JwtTokenizer jwtTokenizer;

    public void saveRefresh(String refreshToken, String value) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(refreshToken,value,jwtTokenizer.getRefreshTokenExpirationMinutes(),TimeUnit.MINUTES);
            //timeout,timeunit
    }

    public void setBlackList(String key, String value, int minutes) {
        redisBlackListTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(value.getClass()));
        String trimedKey = key.trim();
        redisBlackListTemplate.opsForValue().set(trimedKey, value, minutes, TimeUnit.MINUTES);
    }

    public void expireRefreshToken(String key){
        redisTemplate.delete(key);
        //redisTemplate.opsForValue().getAndExpire(key,0, TimeUnit.MINUTES);
    }

    public boolean hasAccess(String access){
         boolean haskey =Boolean.TRUE.equals(redisBlackListTemplate.hasKey(access));
         return haskey;
    }

    public boolean hasRefresh(String refresh){
        boolean haskey =Boolean.TRUE.equals(redisTemplate.hasKey(refresh));
        return haskey;
    }
}
