package teamScanner.dto;

import lombok.Data;

@Data
public class RegistrationRequestDto {
    private String login;
    private String password;
    private String city;
    private int age;
}
