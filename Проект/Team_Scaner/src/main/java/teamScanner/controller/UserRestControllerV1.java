package teamScanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamScanner.dto.AdminUserDto;
import teamScanner.dto.UserDto;
import teamScanner.model.User;
import teamScanner.repository.UserRepository;
import teamScanner.service.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/api/v1/users/")
public class UserRestControllerV1 {
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserRestControllerV1(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDto result = UserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "change_user")
    public ResponseEntity<AdminUserDto> changeUser(@RequestBody UserDto userDto) {
        User user = userService.findById(userDto.getId());
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);


        if (!userService.matchesPassword(userDto.getOldPass(), user.getPassword()))
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        if (userDto.getLogin() != null)
            user.setLogin(userDto.getLogin());
        if (userDto.getPassword() != null)
            user.setPassword(userService.encodePassword(userDto.getPassword()));
        if (userDto.getCity() != null)
            user.setCity(userDto.getCity());
        if (userDto.getAge() != null)
            user.setAge(userDto.getAge());

        userRepository.save(user);

        AdminUserDto result = AdminUserDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "cites")
    public ResponseEntity<List<String>> getCity() throws IOException {
        List<String> lines = Files.lines(Paths.get("src\\main\\resources\\city.txt")).sorted().collect(Collectors.toList());
        return new ResponseEntity<>(lines, HttpStatus.OK);
    }

}
