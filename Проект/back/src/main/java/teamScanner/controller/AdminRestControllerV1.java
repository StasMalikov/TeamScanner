package teamScanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import teamScanner.dto.*;
import teamScanner.model.*;
import teamScanner.repository.RoleRepository;
import teamScanner.repository.UserRepository;
import teamScanner.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminRestControllerV1 {
    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
//    private final EventRepository eventRepository;

    @Autowired
    public AdminRestControllerV1(UserService userService, UserRepository userRepository, RoleRepository roleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
//        this.eventRepository = eventRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    @PostMapping(value = "get_moder_by_login")
    public ResponseEntity<AdminUserDto> getModerByLogin(@RequestBody StringDTO stringDTO) {
        ResponseEntity<List<AdminUserDto>> moders = getModers();
//        AdminUserDto adminUserDto;
        List<AdminUserDto> body = moders.getBody();
        for (AdminUserDto userDto : body) {
            if(userDto.getLogin().equals(stringDTO.getInfo())) {
                return new ResponseEntity<>(userDto, HttpStatus.OK);
//                adminUserDto = userDto;
            }
        }
//       if(adminUserDto == null)return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        User byLogin = userRepository.findByLogin(stringDTO.getInfo());
////        AdminUserDto adminUserDto = AdminUserDto.fromUser(byLogin);
////        return new ResponseEntity<>(adminUserDto, HttpStatus.OK);
//
//        if (byLogin != null)
//            return new ResponseEntity<>(adminUserDto, HttpStatus.OK);
//        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    @PostMapping(value = "get_all_moders")
    public ResponseEntity<List<AdminUserDto>> getModers() {
        List<User> moders = new ArrayList<>();
        Role mod = roleRepository.findByName("ROLE_MODER");
        Role adm = roleRepository.findByName("ROLE_ADMIN");
        userRepository.findAll().forEach(p -> {

            if (p.getRoles().contains(mod) && !p.getRoles().contains(adm)) {
                moders.add(p);
            }
        });
        List<AdminUserDto> collect1 = moders.stream().map(AdminUserDto::fromUser).collect(Collectors.toList());
        if (collect1.size() > 0)
            return new ResponseEntity<>(collect1, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    @PostMapping(value = "get_all_users")
    public ResponseEntity<List<AdminUserDto>> getUsers() {
        List<User> users = new ArrayList<>();
        Role mod = roleRepository.findByName("ROLE_MODER");
        Role adm = roleRepository.findByName("ROLE_ADMIN");
        userRepository.findAll().forEach(p -> {
            if (!p.getRoles().contains(mod) && !p.getRoles().contains(adm)) {
                users.add(p);
            }
        });
//        List<User> all = userRepository.findAll();
        List<AdminUserDto> collect1 = users.stream().map(AdminUserDto::fromUser).collect(Collectors.toList());
        if (collect1.size() > 0)
            return new ResponseEntity<>(collect1, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    @PostMapping(value = "get_all_admins")
    public ResponseEntity<List<AdminUserDto>> getAdmins() {
        List<User> users = new ArrayList<>();
        Role adm = roleRepository.findByName("ROLE_ADMIN");
        userRepository.findAll().forEach(p -> {
            if (p.getRoles().contains(adm)) {
                users.add(p);
            }
        });
        List<AdminUserDto> collect1 = users.stream().map(AdminUserDto::fromUser).collect(Collectors.toList());
        if (collect1.size() > 0)
            return new ResponseEntity<>(collect1, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "get_user_by_name")
    public ResponseEntity<AdminUserDto> getUserByName(@RequestBody StringDTO stringDTO) {
        User byLogin = userRepository.findByLogin(stringDTO.getInfo());
        AdminUserDto adminUserDto = AdminUserDto.fromUser(byLogin);
//        return new ResponseEntity<>(adminUserDto, HttpStatus.OK);

        if (byLogin != null)
            return new ResponseEntity<>(adminUserDto, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "set_role")
    public ResponseEntity<AdminUserDto> setRole(@RequestBody UserStatusDTO userStatusDTO) {
        User user = userService.findByUsername(userStatusDTO.getUserName());

        if (user == null || userStatusDTO.getStatus() == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        if (userStatusDTO.getStatus().toLowerCase().contains("moder"))
            user = userService.setUserRole(user, "ROLE_MODER");
        if (userStatusDTO.getStatus().toLowerCase().contains("admin"))
            user = userService.setUserRole(user, "ROLE_ADMIN");
//        if (!user.equals(userService.findByUsername(statusDTO.getUserName())))
        userRepository.save(user);
//        else   return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        AdminUserDto result = AdminUserDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "rem_role")
    public ResponseEntity<AdminUserDto> removeRole(@RequestBody UserStatusDTO userStatusDTO) {
        User user = userService.findByUsername(userStatusDTO.getUserName());

        if (user == null || userStatusDTO.getStatus() == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        if (userStatusDTO.getStatus().toLowerCase().contains("moder"))
            user = userService.removeUserRole(user, "ROLE_MODER");
        if (userStatusDTO.getStatus().toLowerCase().contains("admin"))
            user = userService.removeUserRole(user, "ROLE_ADMIN");
//        if (!user.equals(userService.findByUsername(statusDTO.getUserName())))
        userRepository.save(user);
//        else   return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        AdminUserDto result = AdminUserDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
