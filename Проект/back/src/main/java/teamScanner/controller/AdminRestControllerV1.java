package teamScanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamScanner.dto.*;
import teamScanner.model.*;
import teamScanner.repository.EventRepository;
import teamScanner.repository.SearchSpecification;
import teamScanner.repository.UserRepository;
import teamScanner.service.UserService;

import java.sql.Struct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminRestControllerV1 {
    private final UserService userService;
    private final UserRepository userRepository;
//    private final EventRepository eventRepository;

    @Autowired
    public AdminRestControllerV1(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
//        this.eventRepository = eventRepository;
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
//
//    @GetMapping(value = "get_user/{id}")
//    public ResponseEntity<AdminUserDto> getUserById(@PathVariable(name = "id") Long id) {
//        User user = userService.findById(id);
//        if (user == null)
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        AdminUserDto result = AdminUserDto.fromUser(user);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
////    @GetMapping(value = "set_moder/{id}")
////    public ResponseEntity<AdminUserDto> setModerRole(@PathVariable(name = "id") Long id) {
////        User user = userService.findById(id);
////        if (user == null)
////            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
////
////        user = userService.setUserRole(user, "ROLE_MODER");
////        userRepository.save(user);
////        AdminUserDto result = AdminUserDto.fromUser(user);
////        return new ResponseEntity<>(result, HttpStatus.OK);
////    }
////
////    @GetMapping(value = "set_admin/{id}")
////    public ResponseEntity<AdminUserDto> setAdminRole(@PathVariable(name = "id") Long id) {
////        User user = userService.findById(id);
////        if (user == null)
////            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
////
////        user = userService.setUserRole(user, "ROLE_ADMIN");
////        userRepository.save(user);
////        AdminUserDto result = AdminUserDto.fromUser(user);
////
////        return new ResponseEntity<>(result, HttpStatus.OK);
////    }
////
////    @GetMapping(value = "remove_admin/{id}")
////    public ResponseEntity<AdminUserDto> removeAdminRole(@PathVariable(name = "id") Long id) {
////        User user = userService.findById(id);
////        if (user == null)
////            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
////
////        user = userService.removeUserRole(user, "ROLE_ADMIN");
////        userRepository.save(user);
////        AdminUserDto result = AdminUserDto.fromUser(user);
////        return new ResponseEntity<>(result, HttpStatus.OK);
////    }
////
////    @GetMapping(value = "remove_moder/{id}")
////    public ResponseEntity<AdminUserDto> removeModerRole(@PathVariable(name = "id") Long id) {
////        User user = userService.findById(id);
////        if (user == null)
////            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
////        user = userService.removeUserRole(user, "ROLE_MODER");
////        userRepository.save(user);
////        AdminUserDto result = AdminUserDto.fromUser(user);
////        return new ResponseEntity<>(result, HttpStatus.OK);
////    }
//
////    //пока не написал
////    @GetMapping(value = "set_status/{id}")
////    public ResponseEntity<AdminUserDto> setStatus(@PathVariable(name = "id") Long id) {
////        User user = userService.findById(id);
////        if (user == null)
////            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
////        AdminUserDto result = AdminUserDto.fromUser(user);
////        return new ResponseEntity<>(result, HttpStatus.OK);
////    }
//
//    private void setStatus(BaseEntity entity, String status) {
//        String strSt = status.toLowerCase();
//        if (strSt.contains("active"))
//            entity.setStatus(Status.ACTIVE);
//        if (strSt.contains("not") && strSt.contains("active"))
//            entity.setStatus(Status.NOT_ACTIVE);
//        if (strSt.contains("ban"))
//            entity.setStatus(Status.BANNED);
//    }
//
//    @PostMapping(value = "set_userStatus")
//    public ResponseEntity<AdminUserDto> setUserStatus(@RequestBody UserStatusDTO userStatusDTO) {
//        User user = userService.findByUsername(userStatusDTO.getUserName());
//        if (user == null || userStatusDTO.getStatus() == null)
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//
//        setStatus(user, userStatusDTO.getStatus());
//
////        String strSt = userStatusDTO.getStatus().toLowerCase();
////        if (strSt.contains("active"))
////            user.setStatus(Status.ACTIVE);
////        if (strSt.contains("not") && strSt.contains("active"))
////            user.setStatus(Status.NOT_ACTIVE);
////        if (strSt.contains("ban"))
////            user.setStatus(Status.BANNED);
//////        if (!user.equals(userService.findByUsername(statusDTO.getUserName())))
//        userRepository.save(user);
////        else return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
//        AdminUserDto result = AdminUserDto.fromUser(user);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    @PostMapping(value = "set_eventStatus")
//    public ResponseEntity<AdminUserDto> setEventStatus(@RequestBody EventStatusDTO eventStatusDTO) {
//        Event event = eventRepository.findById(eventStatusDTO.getEventID()).get();
//        setStatus(event, eventStatusDTO.getStatus());
//
////        String strSt = eventStatusDTO.getStatus().toLowerCase();
////        if (strSt.contains("active"))
////            event.setStatus(Status.ACTIVE);
////        if (strSt.contains("not") && strSt.contains("active"))
////            event.setStatus(Status.NOT_ACTIVE);
////        if (strSt.contains("ban"))
////            event.setStatus(Status.BANNED);
//        eventRepository.save(event);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @PostMapping(value = "ban_listUsers")
//    public ResponseEntity<List<AdminUserDto>> getBanListUsers() {
//        List<User> banned = userRepository.findByStatus(Status.BANNED);
//        List<AdminUserDto> collect = banned.stream().map(AdminUserDto::fromUser).collect(Collectors.toList());
//        return new ResponseEntity<>(collect, HttpStatus.OK);
//    }
//
//    @PostMapping(value = "ban_listEvents")
//    public ResponseEntity<List<EventDTO>> getBanListEvents() {
//        List<Event> banned = eventRepository.findByStatus(Status.BANNED);
//        List<EventDTO> collect = banned.stream().map(EventDTO::fromEvent).collect(Collectors.toList());
//        return new ResponseEntity<>(collect, HttpStatus.OK);
//    }
//
//    @PostMapping(value = "usersByName")
//    public ResponseEntity<AdminUserDto> getUsersByName(@RequestBody FindByNameDto nameDto) {
//        User byLogin = userRepository.findByLogin(nameDto.getName());
//        if (byLogin == null)
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        return new ResponseEntity<>(AdminUserDto.fromUser(byLogin), HttpStatus.OK);
//    }
//
////    @PostMapping(value = "eventsByName")
////    public ResponseEntity<List<EventDTO>> getEventsByName(@RequestBody FindByNameDto nameDto) {
////        List<Event> banned = eventRepository.findByName(nameDto.getName());
////        if (banned.size() < 1)
////            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
////        List<EventDTO> collect = banned.stream().map(EventDTO::fromEvent).collect(Collectors.toList());
////        return new ResponseEntity<>(collect, HttpStatus.OK);
////    }
//
//
//    @GetMapping("/pageable_users/{page}")
//    @ResponseBody
//    public Page<AdminUserDto> getPageableUsers(@PathVariable(value = "page") int page) {
//        Page<User> pageable = userRepository.findPageable(PageRequest.of(page, 10));
//        Page<AdminUserDto> map = pageable.map(AdminUserDto::fromUser);
//
//
//        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "lastName"));
//        return map;
//    }

//    @PostMapping(value = "sort_events")
//    public ResponseEntity<List<EventDTO>> getSortedEvents(@RequestBody EventDTO eventDTO) {
//        Event event = new Event();
//        Map<String, Object> map = new HashMap<>();
//        if (eventDTO.getName() != null)
//            map.put("name", eventDTO.getName());
//        if (eventDTO.getCategory() != null) {
//            String category = eventDTO.getCategory().toLowerCase();
//            if (category.contains("футбол"))
//                map.put("category", Category.FOOTBALL);
//            if (category.contains("волейбол"))
//                map.put("category", Category.VOLLEYBALL);
//            if (category.contains("баскетбол"))
//                map.put("category", Category.BASKETBALL);
//        }
//        if (eventDTO.getAddress() != null)
//            map.put("address", eventDTO.getAddress());
//        if (eventDTO.getDateEvent() != null)
//            map.put("dateEvent", eventDTO.getDateEvent());
//
//        SearchSpecification search = new SearchSpecification(map);
//        List<EventDTO> all = eventRepository.findAll(Specification.where(search)).stream().map(EventDTO::fromEvent).collect(Collectors.toList());
//
////        ExampleMatcher matcher = ExampleMatcher.matching()
////                .withIncludeNullValues()
////                .withIgnoreNullValues();
////
////        Example<Event> ex = Example.of(event, matcher);
////        List<Event> all12 = eventRepository.findAll(ex);
////
////        List<EventDTO> all = eventRepository.findAll(ex).stream().map(EventDTO::fromEvent).collect(Collectors.toList());
//
//        return new ResponseEntity<>(all, HttpStatus.OK);
//    }
}
