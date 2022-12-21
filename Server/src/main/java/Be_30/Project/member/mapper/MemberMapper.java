package Be_30.Project.member.mapper;

import Be_30.Project.member.dto.MemberDto;
import Be_30.Project.member.entity.Member;
import Be_30.Project.member.dto.MemberDto.Response;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member UserPostDtoToUser(MemberDto.Post UserPostDto);
    Member UserPatchDtoToUser(MemberDto.Patch UserPatchDto);
    MemberDto.Response UserToUserResponseDto(Member Member);
    List<Response> UsersToUserResponseDtos(List<Member> members);
}
