package teamScanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamScanner.dto.*;
import teamScanner.model.*;
import teamScanner.repository.UserRepository;
import teamScanner.service.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
@RequestMapping(value = "/api/v1/users/")
public class UserRestControllerV1 {
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserRestControllerV1(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDto result = UserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "change_user")
    public ResponseEntity<AdminUserDto> changeUser(@RequestBody UserDto userDto) {
        User user = userService.findById(userDto.getId());
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);


        if (!userService.matchesPassword(userDto.getOldPass(), user.getPassword()))
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        if (userDto.getLogin() != null)
            user.setLogin(userDto.getLogin());
        if (userDto.getPassword() != null)
            user.setPassword(userService.encodePassword(userDto.getPassword()));
        if (userDto.getCity() != null)
            user.setCity(userDto.getCity());
        if (userDto.getAge() != null)
            user.setAge(userDto.getAge());

        userRepository.save(user);

        AdminUserDto result = AdminUserDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "cites")
    public ResponseEntity<List<String>> getCity() throws IOException {
        List<String> lines = Files.lines(Paths.get("src\\main\\resources\\city.txt")).sorted().collect(Collectors.toList());
        return new ResponseEntity<>(lines, HttpStatus.OK);
    }

//    @PostMapping(value = "add_event")
//    public ResponseEntity<AdminUserDto> addEvent(@RequestBody EventDTO eventDTO) {
//        User user = userService.findById(eventDTO.getCreator_id());
//        if (user == null)
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        List<Event> events = user.getEvents();
//        Event event = new Event();
//        event.setName(eventDTO.getName());
//        event.setDescription(eventDTO.getDescription());
//        String category = eventDTO.getCategory().toLowerCase();
//        if (category.contains("футбол"))
//            event.setCategory(Category.FOOTBALL);
//        if (category.contains("волейбол"))
//            event.setCategory(Category.VOLLEYBALL);
//        if (category.contains("баскетбол"))
//            event.setCategory(Category.BASKETBALL);
//
//        event.setAddress(eventDTO.getAddress());
//        event.setCreator_id(eventDTO.getCreator_id());
//        List<User> users = new ArrayList<>();
//        users.add(user);
//        event.setParticipants(users);
//        event.setComments(new ArrayList<Comment>());
//        event.setCreated(new Date());
//        event.setStatus(Status.ACTIVE);
//        event.setUpdated(new Date());
//        events.add(event);
//
//        user.setEvents(events);
//
//        eventRepository.save(event);
//        userRepository.save(user);
//
//        AdminUserDto result = AdminUserDto.fromUser(user);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    @PostMapping(value = "rem_event")
//    public ResponseEntity<AdminUserDto> removeEvent(@RequestBody MiniEventDTO deleteEventDTO) {
////        Event event = eventRepository.findById(deleteEventDTO.getEventID()).get();
////        List<User> participants = event.getParticipants();
////        for (User us : participants) {
////            for (Event ev : us.getEvents()) {
////                if (ev.getId() == deleteEventDTO.getEventID()) {
////                    us.getEvents().remove(ev);
////                    userRepository.save(us);
////                    break;
////                }
////            }
////        }
////        event.setStatus(Status.NOT_ACTIVE);
////        eventRepository.save(event);
//        if (eventRepository.existsById(deleteEventDTO.getEventID()))
//            eventRepository.deleteById(deleteEventDTO.getEventID());
//        else
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @PostMapping(value = "subscribe")
//    public ResponseEntity<AdminUserDto> subscribeOnEvent(@RequestBody MiniEventDTO subscribeEventDTO) {
//        User user = userRepository.findByLogin(subscribeEventDTO.getUserName());
//        Event event = eventRepository.findById(subscribeEventDTO.getEventID()).get();
//        if (!user.getEvents().contains(event)) {
//            user.getEvents().add(eventRepository.findById(subscribeEventDTO.getEventID()).get());
//            userRepository.save(user);
//        } else return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @PostMapping(value = "unsubscribe")
//    public ResponseEntity<AdminUserDto> unsubscribeOnEvent(@RequestBody MiniEventDTO unsubscribeEventDTO) {
//        User user = userRepository.findByLogin(unsubscribeEventDTO.getUserName());
//        user.getEvents().remove(eventRepository.findById(unsubscribeEventDTO.getEventID()).get());
//        userRepository.save(user);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

//    @PostMapping(value = "add_comment")
//    public ResponseEntity<AdminUserDto> addComment(@RequestBody CommentEventDTO commentEventDTO) {
//        User user = userRepository.findByLogin(commentEventDTO.getUserName());
//        Event event = eventRepository.findById(commentEventDTO.getEventID()).get();
//
//        Comment comment = new Comment();
//        comment.setCreated(new Date());
//        comment.setCreator(user);
//        comment.setEvent(event);
//        comment.setStatus(Status.ACTIVE);
//        comment.setUpdated(new Date());
//        comment.setCommentText(commentEventDTO.getComment());
//        commentRepository.save(comment);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @PostMapping(value = "rem_comment")
//    public ResponseEntity<AdminUserDto> removeComment(@RequestBody CommentEventDTO commentEventDTO) {
//        if (commentRepository.existsById(commentEventDTO.getCommentID()))
//            commentRepository.deleteById(commentEventDTO.getCommentID());
//        else
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

//    @PostMapping(value = "change_event")
//    public ResponseEntity<AdminUserDto> changeEvent(@RequestBody EventDTO eventDTO) {
//        Event event = eventRepository.findById(eventDTO.getEventID()).get();
//
//        if (eventDTO.getName() != null)
//            event.setName(eventDTO.getName());
//        if (eventDTO.getDescription() != null)
//            event.setDescription(eventDTO.getDescription());
//
//        String category = eventDTO.getCategory();
//        if (category != null) {
//            if (category.contains("футбол"))
//                event.setCategory(Category.FOOTBALL);
//            if (category.contains("волейбол"))
//                event.setCategory(Category.VOLLEYBALL);
//            if (category.contains("баскетбол"))
//                event.setCategory(Category.BASKETBALL);
//        }
//        if (eventDTO.getAddress() != null)
//            event.setAddress(eventDTO.getAddress());
//        if (eventDTO.getCreator_id() != null && eventDTO.getCreator_id() > 0)
//            event.setCreator_id(eventDTO.getCreator_id());
//
//        eventRepository.save(event);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @GetMapping("/pageable_events/{page}")
//    @ResponseBody
//    public Page<EventDTO> getPageableEvents(@PathVariable(value = "page") int page) {
//        Page<Event> pageable = eventRepository.findPageable(PageRequest.of(page, 10));
//        Page<EventDTO> map = pageable.map(EventDTO::fromEvent);
//        return map;
//    }
//
//    @PostMapping(value = "get_events")
//    public ResponseEntity<List<EventDTO>> getEvent() {
//        List<EventDTO> collect = eventRepository.findAll().stream().map(EventDTO::fromEvent).collect(Collectors.toList());
//        return new ResponseEntity<>(collect, HttpStatus.OK);
//    }
//
//    @PostMapping(value = "eventsByName")
//    public ResponseEntity<List<EventDTO>> getEventsByName(@RequestBody FindByNameDto nameDto) {
//        List<Event> banned = eventRepository.findByName(nameDto.getName());
//        if (banned.size() < 1)
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        List<EventDTO> collect = banned.stream().map(EventDTO::fromEvent).collect(Collectors.toList());
//        return new ResponseEntity<>(collect, HttpStatus.OK);
//    }

}
