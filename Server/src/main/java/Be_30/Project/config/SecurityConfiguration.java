package Be_30.Project.config;

import static org.springframework.security.config.Customizer.withDefaults;

import Be_30.Project.auth.filter.JwtAuthenticationFilter;
import Be_30.Project.auth.jwt.JwtTokenizer;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfiguration {
    private final JwtTokenizer jwtTokenizer;

    public SecurityConfiguration(JwtTokenizer jwtTokenizer) {
        this.jwtTokenizer = jwtTokenizer;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
            .headers().frameOptions().sameOrigin() // h2 웹 콘솔 사용
            .and()
            .csrf().disable()
            .cors(withDefaults())
            .formLogin().disable() // csr 방식에서 사용하는 json포맷으로 정보전송하기 위함
            .httpBasic().disable()
            .apply(new CustomFilterConfigurer())
            .and()
            .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    //cors 정책 설정
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        //모든 origin에 대해 접근 가능
        configuration.setAllowedOrigins(Arrays.asList("*"));
        //"GET","POST", "PATCH", "DELETE" 메서드에 대한 통신 허용
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PATCH", "DELETE"));
        // CorsConfigurationSource 인터페이스의 구현 클래스인 UrlBasedCorsConfigurationSource  객체 생성
        /*====================여기까지 cors 정책을 구성=========================*/

        /*====================구성한 cors 정책을 적용=========================*/
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    public class CustomFilterConfigurer extends
        AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        public void configure(HttpSecurity builder){
            AuthenticationManager authenticationManager
                = builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);
            jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");

            builder.addFilter(jwtAuthenticationFilter);
        }

    }
}
