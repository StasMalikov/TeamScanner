package teamScanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamScanner.dto.AuthenticationRequestDto;
import teamScanner.dto.RegistrationRequestDto;
import teamScanner.model.User;
import teamScanner.repository.UserRepository;
import teamScanner.security.jwt.JwtTokenProvider;
import teamScanner.service.UserService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("login", username);
            response.put("token", token);
            response.put("roles", user.getRolesStr());
            response.put("id", user.getId());
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("registration")
    public ResponseEntity registration(@RequestBody RegistrationRequestDto requestDto) {
//        try {
        String login = requestDto.getLogin();

        if (userRepository.findByLogin(login) != null) {
            return new ResponseEntity<>(HttpStatus.FOUND);
//                throw new UsernameNotFoundException("User with username: " + login + " already");
        }


        String city = requestDto.getCity();
        Date age = requestDto.getAge();
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, requestDto.getPassword()));
        User user = new User();
        user.setLogin(login);
        user.setPassword(requestDto.getPassword());
        user.setCity(city);
        user.setAge(age);
        user.setCreated(new Date());
        user.setUpdated(new Date());

        user = userService.register(user);//шифруется тут

        User saved =  userRepository.save(user);

        String token = jwtTokenProvider.createToken(login, user.getRoles());

        Map<Object, Object> response = new HashMap<>();
        response.put("login", login);
        response.put("token", token);
        response.put("roles", user.getRolesStr());
        response.put("id", saved.getId());

        return ResponseEntity.ok(response);
//        } catch (AuthenticationException e) {
//            throw new BadCredentialsException("Invalid username or password");
//        }
    }
}
