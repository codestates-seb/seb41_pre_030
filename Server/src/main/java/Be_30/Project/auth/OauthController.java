package Be_30.Project.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/login/oauth2")
@RestController
public class OauthController {

    // 1. http://localhost:8080/oauth2/authorization/google 링크 클릭
    // 2. 스프링 시큐리티가 yml 파일을 기반으로 소셜 로그인 페이지 경로로 리다이렉트
    // 3. 클라이언트가 로그인 성공
    // 4. 설정된 리다이렉트 경로로 이동 -> 백엔드로 authorization code 전달
    // 5. 백엔드에서 다시 code를 이용해 사용자 정보를 조회
    // 6. 서버 앱에 맞는 jwt 토큰을 새로 만들어서 전달

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String google_client_id;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String google_client_secret;

    private final String google_request_uri = "https://oauth2.googleapis.com/token";

    @GetMapping("/code/google")
    public void login(String code) {
        log.info(">>>> 클라이언트 구글 로그인 성공: {}", code);

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", google_client_id);
        params.add("client_secret", google_client_secret);
//        params.add("redirect_uri", google_redirect_uri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<?> entity = new HttpEntity<>(params, headers);

        ResponseEntity<String> result = restTemplate.exchange(
                google_request_uri,
                HttpMethod.POST,
                entity,
                String.class
        );

        log.info(">>>>>>>> 결과는? {}", result);
    }

}
