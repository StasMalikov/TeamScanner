package teamScanner.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RegistrationRequestDto {
    private String login;
    private String password;
    private String city;
    private Date age;
}
