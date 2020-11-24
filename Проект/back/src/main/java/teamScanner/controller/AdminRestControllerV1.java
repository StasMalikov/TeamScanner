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
import teamScanner.service.AdminRestService;
import teamScanner.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminRestControllerV1 {
    private final AdminRestService adminRestService;

    @Autowired
    public AdminRestControllerV1(AdminRestService adminRestService) {
        this.adminRestService = adminRestService;
    }

    @Transactional
    @PostMapping(value = "get_moder_by_login")
    public ResponseEntity<AdminUserDto> getModerByLogin(@RequestBody StringDTO stringDTO) {
        return adminRestService.getREA(adminRestService.getModerByLogin(stringDTO));
    }

    @Transactional
    @PostMapping(value = "get_all_moders")
    public ResponseEntity<List<AdminUserDto>> getModers() {
        return adminRestService.getRELA(adminRestService.getModers());
    }

    @Transactional
    @PostMapping(value = "get_all_users")
    public ResponseEntity<List<AdminUserDto>> getUsers() {
        return adminRestService.getRELA(adminRestService.getUsers());
    }

    @Transactional
    @PostMapping(value = "get_all_admins")
    public ResponseEntity<List<AdminUserDto>> getAdmins() {
        return adminRestService.getRELA(adminRestService.getAdmins());
    }

    @Transactional
    @PostMapping(value = "get_user_by_login")
    public ResponseEntity<AdminUserDto> getUserByLogin(@RequestBody StringDTO stringDTO) {
        return adminRestService.getREA(adminRestService.getUserByLogin(stringDTO));
    }

    @PostMapping(value = "set_role")
    public ResponseEntity<AdminUserDto> setRole(@RequestBody UserStatusDTO userStatusDTO) {
        return adminRestService.getREA(adminRestService.setRole(userStatusDTO));
    }

    @PostMapping(value = "rem_role")
    public ResponseEntity<AdminUserDto> removeRole(@RequestBody UserStatusDTO userStatusDTO) {
        return adminRestService.getREA(adminRestService.removeRole(userStatusDTO));
    }
}
