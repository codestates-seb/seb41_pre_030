package Be_30.Project.file.dto;

import Be_30.Project.member.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;

public class FileDto {

    @Getter
    @Builder
    public static class Response {
        private String fileName;
        private String src;
        private MemberDto.ResponseOnlyMember member;
    }
}
