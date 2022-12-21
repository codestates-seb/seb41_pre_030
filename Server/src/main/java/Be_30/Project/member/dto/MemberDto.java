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

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch{

        private long memberId;

        private String email;

        private String nickName;

        private MemberStatus memberStatus;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response{
        private long memberId;

        private String email;

        private String nickName;

        private MemberStatus memberStatus;
    }

}
