package teamScanner.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import teamScanner.model.Role;
import teamScanner.model.Status;
import teamScanner.model.User;

import java.util.Date;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserDto {
    private Long id;
    private String login;
    private String status;
    private String city;
    private Date age;
    private String roles;

    public  User toUser() {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setCity(city);
        user.setAge(age);
        user.setStatus(Status.valueOf(status));
        return user;
    }

    public static AdminUserDto fromUser(User user) {
        AdminUserDto adminUserDto = new AdminUserDto();
        adminUserDto.setId(user.getId());
        adminUserDto.setLogin(user.getLogin());
        adminUserDto.setCity(user.getCity());
        adminUserDto.setAge(user.getAge());
        adminUserDto.setStatus(user.getStatus().name());

        String userRoles = "";
        for (Role role : user.getRoles()) {
            userRoles += role.getName() + " ";
        }

        adminUserDto.setRoles(userRoles.trim());
        return adminUserDto;
    }
}
