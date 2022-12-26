package Be_30.Project.auth.handler;

import Be_30.Project.dto.ErrorResponse;
import Be_30.Project.dto.LoginDto;
import Be_30.Project.member.entity.Member;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
        throws IOException, ServletException {
        // Authentication 객체에 사용자 정보를 얻은 후, HttpServletResponse로
        // 출력 스트림을 생성하여 response를 전송할 수 있다
        log.info("# Authenticated successfully!");
        //sendSuccessResponse(response,authentication);
    }

    private void sendSuccessResponse(HttpServletResponse response,Authentication authentication)
        throws IOException {
//        Gson gson = new Gson();
//        Member member = (Member)authentication.getPrincipal();
//        LoginDto.Response responseDto = new Response();
//        responseDto.setUserId(member.getMemberId());
//        responseDto.setUsername(member.getNickName());
//
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setStatus(HttpStatus.OK.value());
//        response.getWriter().write(gson.toJson(responseDto, ErrorResponse.class));
    }
}
