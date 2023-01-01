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

    public void saveRefresh(Long memberId, String refreshToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(memberId.toString(),refreshToken,jwtTokenizer.getRefreshTokenExpirationMinutes(),TimeUnit.MINUTES);
            //timeout,timeunit


    }

    public void setBlackList(String key, String value, int minutes) {
        redisBlackListTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(value.getClass()));
        redisBlackListTemplate.opsForValue().set(key, value, minutes, TimeUnit.MINUTES);
    }

    public void expireRefreshToken(String key){
        //redisTemplate.delete(key);
        redisTemplate.opsForValue().getAndExpire(key,0, TimeUnit.MINUTES);
    }


}
