package Be_30.Project.user.controller;

import Be_30.Project.user.entity.User.UserStatus;
import Be_30.Project.user.dto.UserDto;
import Be_30.Project.user.mapper.UserMapper;
import Be_30.Project.user.repository.UserRepository;
import java.net.URI;
import java.util.Arrays;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    UserRepository userRepository;
    UserMapper userMapper;

    public UserController(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity postUser(){
        return ResponseEntity.created(URI.create("/v1/users/1")).build();
    }

    @PatchMapping("/{user-id}")
    public ResponseEntity patchUser(){
        UserDto.Response response =
            new UserDto.Response(1,"heebum@gmail.com",
                "희범", "1234", UserStatus.USER_ACTIVE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{user-id}")
    public ResponseEntity getUser(){
        UserDto.Response response =
            new UserDto.Response(1,"heebum@gmail.com",
                "희범", "1234", UserStatus.USER_ACTIVE);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity deleteUser(){
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity getUsers(){
        UserDto.Response response1 =
            new UserDto.Response(1,"heebum1@gmail.com",
                "희범1", "1111", UserStatus.USER_ACTIVE);

        UserDto.Response response2 =
            new UserDto.Response(2,"heebum2@gmail.com",
                "희범2", "2222", UserStatus.USER_ACTIVE);
        return ResponseEntity.ok(Arrays.asList(response1, response2));
    }
}
