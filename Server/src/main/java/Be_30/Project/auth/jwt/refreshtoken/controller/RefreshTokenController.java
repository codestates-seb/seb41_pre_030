package Be_30.Project.auth.jwt.refreshtoken.controller;


import Be_30.Project.auth.jwt.refreshtoken.service.RefreshTokenService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    public RefreshTokenController(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    @DeleteMapping("/members/logout")
    public void logoutMember(HttpServletRequest request, HttpServletResponse response){

        //====================================
        refreshTokenService.logoutMember(request.getHeader("Refresh"));

        Cookie cookie = new Cookie("RefreshToken", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        Cookie mbCookie = new Cookie("MemberId", null);
        mbCookie.setMaxAge(0);
        mbCookie.setPath("/");

        response.addCookie(cookie);
        response.addCookie(mbCookie);
    }
}
