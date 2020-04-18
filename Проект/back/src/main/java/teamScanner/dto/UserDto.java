package teamScanner.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import teamScanner.model.User;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String login;
    private String password;
    private String city;
    private int age;
//    private String email;

    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setCity(city);
        user.setAge(age);
//        user.setEmail(email);

        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setLogin(user.getLogin());
        userDto.setCity(user.getCity());
        userDto.setAge(user.getAge());
//        userDto.setEmail(user.getEmail());

        return userDto;
    }
}
