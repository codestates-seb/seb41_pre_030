package Be_30.Project.member.dto;

import Be_30.Project.member.entity.Member.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class MemberDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post{

        private String email;

        private String nickName;

        private String password;

        private MemberStatus userStatus;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch{

        private long userId;

        private String email;

        private String nickName;

        private String password;

        private MemberStatus userStatus;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response{
        private long userId;

        private String email;

        private String nickName;

        private String password;

        private MemberStatus userStatus;
    }

}
