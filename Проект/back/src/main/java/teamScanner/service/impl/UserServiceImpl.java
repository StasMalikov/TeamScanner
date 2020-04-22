package teamScanner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import teamScanner.model.Role;
import teamScanner.model.Status;
import teamScanner.model.User;
import teamScanner.repository.RoleRepository;
import teamScanner.repository.UserRepository;
import teamScanner.service.UserService;

import java.util.ArrayList;
import java.util.List;


@Service
//@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User setUserRole(User user, String roleName) {
        List<Role> userRoles = user.getRoles();
        if (userRoles.size() < 2)
            userRoles.add(roleRepository.findByName(roleName));
        else {
            boolean flag = false;
            for (Role role : userRoles) {
                if (role.getName().equals(roleName))
                    flag = true;
            }
            if (!flag)
                userRoles.add(roleRepository.findByName(roleName));
        }
        user.setRoles(userRoles);
        return user;
    }

    @Override
    public User removeUserRole(User user, String roleName) {
        List<Role> userRoles = user.getRoles();

        userRoles.removeIf(role -> role.getName().equals(roleName));

        user.setRoles(userRoles);
        return user;
    }
    @Override
    public String encodePassword(String password){
        return passwordEncoder.encode(password);
    }

    public boolean matchesPassword(String inputPass, String dbPass){
        return passwordEncoder.matches(inputPass,dbPass);
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.save(user);

//        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
//        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByLogin(username);
//        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
//            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }

//        log.info("IN findById - user: {} found by id: {}", result);
        return result;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
//        log.info("IN delete - user with id: {} successfully deleted");
    }
}
