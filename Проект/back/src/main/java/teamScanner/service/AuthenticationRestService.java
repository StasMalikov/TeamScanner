package teamScanner.service;

import teamScanner.dto.AuthenticationRequestDto;
import teamScanner.dto.RegistrationRequestDto;

import java.util.Map;

public interface AuthenticationRestService {
    Map<Object, Object> login(AuthenticationRequestDto requestDto);
    Map<Object, Object> registration(RegistrationRequestDto requestDto);
}
