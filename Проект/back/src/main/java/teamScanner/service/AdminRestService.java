package teamScanner.service;

import org.springframework.http.ResponseEntity;
import teamScanner.dto.AdminUserDto;
import teamScanner.dto.StringDTO;
import teamScanner.dto.UserStatusDTO;

import java.util.List;

public interface AdminRestService {
    AdminUserDto getModerByLogin(StringDTO stringDTO);
    List<AdminUserDto> getModers();
    List<AdminUserDto> getUsers();
    List<AdminUserDto> getAdmins();
    AdminUserDto getUserByLogin(StringDTO stringDTO);
    AdminUserDto setRole(UserStatusDTO userStatusDTO);
    AdminUserDto removeRole(UserStatusDTO userStatusDTO);
    ResponseEntity<AdminUserDto> getREA(AdminUserDto object);
    ResponseEntity<List<AdminUserDto>> getRELA(List<AdminUserDto> object);
}
