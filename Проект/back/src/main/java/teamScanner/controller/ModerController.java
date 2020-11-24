package teamScanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import teamScanner.dto.*;
import teamScanner.model.*;
import teamScanner.repository.EventRepository;
import teamScanner.repository.RoleRepository;
import teamScanner.repository.UserRepository;
import teamScanner.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/moder/")
public class ModerController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public ModerController(UserService userService, UserRepository userRepository, EventRepository eventRepository, RoleRepository roleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    @GetMapping(value = "get_user/{id}")
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        AdminUserDto result = AdminUserDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private void setStatus(BaseEntity entity, String status) {
        String strSt = status.toLowerCase();
        if (strSt.contains("active"))
            entity.setStatus(Status.ACTIVE);
        if (strSt.contains("not") && strSt.contains("active"))
            entity.setStatus(Status.NOT_ACTIVE);
        if (strSt.contains("ban"))
            entity.setStatus(Status.BANNED);
    }

    @Transactional
    @PostMapping(value = "set_userStatus")
    public ResponseEntity<AdminUserDto> setUserStatus(@RequestBody UserStatusDTO userStatusDTO) {
        User user = userService.findByUsername(userStatusDTO.getUserName());
        if (user == null || userStatusDTO.getStatus() == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        setStatus(user, userStatusDTO.getStatus());

        AdminUserDto result = AdminUserDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Transactional
    @PostMapping(value = "set_eventStatus")
    public ResponseEntity<AdminUserDto> setEventStatus(@RequestBody EventStatusDTO eventStatusDTO) {
        Event event = eventRepository.findById(eventStatusDTO.getEventID()).get();
        setStatus(event, eventStatusDTO.getStatus());
        eventRepository.save(event);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PostMapping(value = "ban_listUsers")
    public ResponseEntity<List<AdminUserDto>> getBanListUsers() {
        List<User> banned = userRepository.findByStatus(Status.BANNED);
        List<AdminUserDto> collect = banned.stream().map(AdminUserDto::fromUser).collect(Collectors.toList());
        if (collect.size() > 0)
            return new ResponseEntity<>(collect, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    @PostMapping(value = "ban_listEvents")
    public ResponseEntity<List<EventDTO>> getBanListEvents() {
        List<Event> banned = eventRepository.findByStatus(Status.BANNED);
        List<EventDTO> collect = banned.stream().map(EventDTO::fromEvent).collect(Collectors.toList());
        if (collect.size() > 0)
            return new ResponseEntity<>(collect, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    @PostMapping(value = "usersByName")
    public ResponseEntity<AdminUserDto> getUsersByName(@RequestBody FindByNameDto nameDto) {
        User byLogin = userRepository.findByLogin(nameDto.getName());
        if (byLogin == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Role mod = roleRepository.findByName("ROLE_MODER");
        Role adm = roleRepository.findByName("ROLE_ADMIN");
        if (!byLogin.getRoles().contains(mod) && !byLogin.getRoles().contains(adm))
            return new ResponseEntity<>(AdminUserDto.fromUser(byLogin), HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @PostMapping(value = "eventsByName")
    public ResponseEntity<List<EventDTO>> getEventsByName(@RequestBody FindByNameDto nameDto) {
        List<Event> banned = eventRepository.findByName(nameDto.getName());
        if (banned.size() < 1)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<EventDTO> collect = banned.stream().map(EventDTO::fromEvent).collect(Collectors.toList());
        return new ResponseEntity<>(collect, HttpStatus.OK);
    }

    @Transactional
    @PostMapping(value = "get_ban_users")
    public ResponseEntity<List<AdminUserDto>> getBanUsers() {
        List<User> byStatus = userRepository.findByStatus(Status.BANNED);
        if (byStatus.size() > 0) {
            List<AdminUserDto> collect = byStatus.stream().map(AdminUserDto::fromUser).collect(Collectors.toList());
            return new ResponseEntity<>(collect, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @PostMapping(value = "get_ban_events")
    public ResponseEntity<List<EventDTO>> getBanEvents() {
        List<Event> byStatus = eventRepository.findByStatus(Status.BANNED);
        if (byStatus.size() > 0) {
            List<EventDTO> collect = byStatus.stream().map(EventDTO::fromEvent).collect(Collectors.toList());
            return new ResponseEntity<>(collect, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @PostMapping(value = "get_all_users")
    public ResponseEntity<List<AdminUserDto>> getUsers() {
//        List<User> all = userRepository.findAll();
        List<AdminUserDto> collect1 = userRepository.findAll().stream().map(AdminUserDto::fromUser).collect(Collectors.toList());
        if (collect1 != null && collect1.size() > 0)
            return new ResponseEntity<>(collect1, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    @PostMapping(value = "get_user_by_name")
    public ResponseEntity<AdminUserDto> getUserByName(@RequestBody StringDTO stringDTO) {
        User byLogin = userRepository.findByLogin(stringDTO.getInfo());
        if (byLogin == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        AdminUserDto adminUserDto = AdminUserDto.fromUser(byLogin);
        return new ResponseEntity<>(adminUserDto, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/pageable_users/{page}")
    @ResponseBody
    public Page<AdminUserDto> getPageableUsers(@PathVariable(value = "page") int page) {
        Page<User> pageable = userRepository.findPageable(PageRequest.of(page, 10));
        Page<AdminUserDto> map = pageable.map(AdminUserDto::fromUser);


        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "lastName"));
        return map;
    }
}
