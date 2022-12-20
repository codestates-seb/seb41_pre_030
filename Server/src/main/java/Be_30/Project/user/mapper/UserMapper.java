package Be_30.Project.user.mapper;

import Be_30.Project.user.entity.User;
import Be_30.Project.user.dto.UserDto;
import Be_30.Project.user.dto.UserDto.Response;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User UserPostDtoToUser(UserDto.Post UserPostDto);
    User UserPatchDtoToUser(UserDto.Patch UserPatchDto);
    UserDto.Response UserToUserResponseDto(User User);
    List<Response> UsersToUserResponseDtos(List<User> Users);
}
