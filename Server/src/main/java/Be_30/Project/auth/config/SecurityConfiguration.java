package Be_30.Project.auth.config;

import static org.springframework.security.config.Customizer.withDefaults;

import Be_30.Project.auth.filter.JwtAuthenticationFilter;
import Be_30.Project.auth.filter.JwtVerificationFilter;
import Be_30.Project.auth.handler.MemberAccessDeniedHandler;
import Be_30.Project.auth.handler.MemberAuthenticationEntryPoint;
import Be_30.Project.auth.handler.MemberAuthenticationFailureHandler;
import Be_30.Project.auth.handler.MemberAuthenticationSuccessHandler;
import Be_30.Project.auth.handler.OAuth2MemberSuccessHandler;
import Be_30.Project.auth.jwt.JwtTokenizer;
import Be_30.Project.auth.utils.CustomAuthorityUtils;
import Be_30.Project.member.service.MemberService;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final MemberService memberService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .headers().frameOptions().sameOrigin()
            .and()
            .csrf().disable() //csrf security 비활성화
            .cors(withDefaults())
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            //세션을 생성하지 않도록 설정
            .and()
            .formLogin().disable()
            .httpBasic().disable()
            .exceptionHandling()
            .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
            .accessDeniedHandler(new MemberAccessDeniedHandler())
            .and()
            .apply(new CustomFilterConfigurer())
            .and()
            .authorizeHttpRequests(authorize -> authorize
//                .antMatchers(HttpMethod.POST, "/members").permitAll()
//                .antMatchers(HttpMethod.PATCH, "/members/**").hasRole("USER")
//                .antMatchers(HttpMethod.GET, "/members").hasRole("ADMIN")
//                .antMatchers(HttpMethod.GET, "/members/**").hasAnyRole("USER", "ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/members/**").hasRole("USER")
//
//                .antMatchers(HttpMethod.POST, "/questions").hasAnyRole("USER", "ADMIN")
//                .antMatchers(HttpMethod.PATCH, "/questions/**").hasAnyRole("USER", "ADMIN")
//                .antMatchers(HttpMethod.GET, "/questions").permitAll()
//                .antMatchers(HttpMethod.GET, "/questions/**").permitAll()
//                .antMatchers(HttpMethod.DELETE, "/questions/**").hasAnyRole("USER", "ADMIN")
//
//                .antMatchers(HttpMethod.POST, "/answers").hasAnyRole("USER", "ADMIN")
//                .antMatchers(HttpMethod.PATCH, "/answers/**").hasAnyRole("USER", "ADMIN")
//                .antMatchers(HttpMethod.GET, "/answers").permitAll()
//                .antMatchers(HttpMethod.GET, "/answers/**").permitAll()
//                .antMatchers(HttpMethod.DELETE, "/answers/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().permitAll());




        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    //cors 정책 설정
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PATCH", "DELETE"));
//        configuration.setExposedHeaders(Arrays.asList("*"));
//        configuration.setAllowedHeaders(Arrays.asList("*"));
        //===========정책 설정

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        //===========정책 적용

        return source;
    }
    //작성한 filter 적용하는 메서드 여기선 jwtAuthenticationFilter 적용
    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);


            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler()); //success 필터추가
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler()); //failure 필터추가
            jwtAuthenticationFilter.setFilterProcessesUrl("/members/login");

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils);

            builder.addFilter(jwtAuthenticationFilter).addFilterAfter(jwtVerificationFilter,JwtAuthenticationFilter.class );
            //안증 후에(after) 필터가 수행되는것이 자연스러움
        }
    }
}
