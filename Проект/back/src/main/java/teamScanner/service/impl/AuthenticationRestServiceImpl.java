package teamScanner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import teamScanner.dto.AuthenticationRequestDto;
import teamScanner.dto.RegistrationRequestDto;
import teamScanner.model.User;
import teamScanner.repository.UserRepository;
import teamScanner.security.jwt.JwtTokenProvider;
import teamScanner.service.AuthenticationRestService;
import teamScanner.service.UserService;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationRestServiceImpl implements AuthenticationRestService {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public AuthenticationRestServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }
    @Override
    public Map<Object, Object> login(AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);
            if (user == null)
                return null;
            String token = jwtTokenProvider.createToken(username, user.getRoles());
            Map<Object, Object> response = new HashMap<>();
            response.put("login", username);
            response.put("token", token);
            response.put("roles", user.getRolesStr());
            response.put("id", user.getId());
            return response;
        } catch (AuthenticationException e) {
            return null;
        }
    }

    @Override
    public Map<Object, Object> registration(RegistrationRequestDto requestDto) {
        return null;
    }
}
