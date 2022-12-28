package Be_30.Project.member.mapper;

import Be_30.Project.member.dto.MemberDto;
import Be_30.Project.member.entity.Member;
import Be_30.Project.member.dto.MemberDto.Response;
import Be_30.Project.question.dto.QuestionDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member MemberPostDtoToMember(MemberDto.Post UserPostDto);
    Member MemberPatchDtoToMember(MemberDto.Patch UserPatchDto);
    MemberDto.Response MemberToMemberResponseDto(Member Member);
    List<Response> MembersToMemberResponseDtos(List<Member> members);



}
