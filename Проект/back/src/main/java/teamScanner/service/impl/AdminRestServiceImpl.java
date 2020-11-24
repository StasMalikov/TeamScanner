package teamScanner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import teamScanner.dto.AdminUserDto;
import teamScanner.dto.StringDTO;
import teamScanner.dto.UserStatusDTO;
import teamScanner.model.Role;
import teamScanner.model.User;
import teamScanner.repository.RoleRepository;
import teamScanner.repository.UserRepository;
import teamScanner.service.AdminRestService;
import teamScanner.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminRestServiceImpl implements AdminRestService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminRestServiceImpl(UserService userService, UserRepository userRepository, RoleRepository roleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    public AdminUserDto getModerByLogin(StringDTO stringDTO) {
        List<User> moders = new ArrayList<>();
        Role mod = roleRepository.findByName("ROLE_MODER");
        Role adm = roleRepository.findByName("ROLE_ADMIN");
        List<User> all = userRepository.findAll();
        all.forEach(p -> {

            if (p.getRoles().contains(mod) && !p.getRoles().contains(adm)) {
                moders.add(p);
            }
        });
        List<AdminUserDto> collect1 = moders.stream().map(AdminUserDto::fromUser).collect(Collectors.toList());
        for (AdminUserDto userDto : collect1) {
            if (userDto.getLogin().equals(stringDTO.getInfo()))
                return userDto;
        }
        return null;
    }

    @Override
    public List<AdminUserDto> getModers() {
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
            return null;
        return collect1;
    }

    @Override
    public List<AdminUserDto> getUsers() {
        List<User> users = new ArrayList<>();
        Role mod = roleRepository.findByName("ROLE_MODER");
        Role adm = roleRepository.findByName("ROLE_ADMIN");
        userRepository.findAll().forEach(p -> {
            if (!p.getRoles().contains(mod) && !p.getRoles().contains(adm)) {
                users.add(p);
            }
        });
        List<AdminUserDto> collect1 = users.stream().map(AdminUserDto::fromUser).collect(Collectors.toList());
        if (collect1.size() > 0)
            return null;
        return collect1;
    }

    @Override
    public List<AdminUserDto> getAdmins() {
        List<User> users = new ArrayList<>();
        Role adm = roleRepository.findByName("ROLE_ADMIN");
        userRepository.findAll().forEach(p -> {
            if (p.getRoles().contains(adm)) {
                users.add(p);
            }
        });
        List<AdminUserDto> collect1 = users.stream().map(AdminUserDto::fromUser).collect(Collectors.toList());
        if (collect1.size() > 0)
            return null;
        return collect1;
    }

    @Override
    public AdminUserDto getUserByLogin(StringDTO stringDTO) {
        User byLogin = userRepository.findByLogin(stringDTO.getInfo());
        if (byLogin == null)
            return null;
        Role mdr = roleRepository.findByName("ROLE_MODER");
        Role adm = roleRepository.findByName("ROLE_ADMIN");
        if (byLogin.getRoles().contains(mdr) || byLogin.getRoles().contains(adm))
            return null;
        AdminUserDto adminUserDto = AdminUserDto.fromUser(byLogin);
        return adminUserDto;
    }


    @Override
    public AdminUserDto setRole(UserStatusDTO userStatusDTO) {
        User user = userService.findByUsername(userStatusDTO.getUserName());
        if (user == null || userStatusDTO.getStatus() == null)
            return null;

        if (userStatusDTO.getStatus().toLowerCase().contains("moder"))
            user = userService.setUserRole(user, "ROLE_MODER");
        if (userStatusDTO.getStatus().toLowerCase().contains("admin"))
            user = userService.setUserRole(user, "ROLE_ADMIN");
        userRepository.save(user);
        AdminUserDto result = AdminUserDto.fromUser(user);
        return result;
    }

    @Override
    public AdminUserDto removeRole(UserStatusDTO userStatusDTO) {
        User user = userService.findByUsername(userStatusDTO.getUserName());
        if (user == null || userStatusDTO.getStatus() == null)
            return null;
        if (userStatusDTO.getStatus().toLowerCase().contains("moder"))
            user = userService.removeUserRole(user, "ROLE_MODER");
        if (userStatusDTO.getStatus().toLowerCase().contains("admin"))
            user = userService.removeUserRole(user, "ROLE_ADMIN");
        userRepository.save(user);
        AdminUserDto result = AdminUserDto.fromUser(user);
        return result;
    }

    @Override
    public ResponseEntity<AdminUserDto> getREA(AdminUserDto object) {
        if (object == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(object, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<AdminUserDto>> getRELA(List<AdminUserDto> object) {
        if (object == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(object, HttpStatus.OK);
    }
}
