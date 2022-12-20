package Be_30.Project.user.dto;

import Be_30.Project.user.entity.User.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class UserDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post{

        private String email;

        private String nickName;

        private String password;

        private UserStatus userStatus;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch{

        private long userId;

        private String email;

        private String nickName;

        private String password;

        private UserStatus userStatus;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response{
        private long userId;

        private String email;

        private String nickName;

        private String password;

        private UserStatus userStatus;
    }

}
