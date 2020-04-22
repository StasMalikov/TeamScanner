package teamScanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamScanner.dto.AdminUserDto;
import teamScanner.dto.EventDTO;
import teamScanner.dto.FindByNameDto;
import teamScanner.dto.MiniEventDTO;
import teamScanner.model.*;
import teamScanner.repository.CommentRepository;
import teamScanner.repository.EventRepository;
import teamScanner.repository.UserRepository;
import teamScanner.service.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/events/")
public class EventController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Autowired
    public EventController(UserService userService, UserRepository userRepository, EventRepository eventRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @PostMapping(value = "add_event")
    public ResponseEntity<AdminUserDto> addEvent(@RequestBody EventDTO eventDTO) {
        User user = userService.findById(eventDTO.getCreator_id());
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        List<Event> events = user.getEvents();
        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        String category = eventDTO.getCategory().toLowerCase();
        if (category.contains("футбол"))
            event.setCategory(Category.FOOTBALL);
        if (category.contains("волейбол"))
            event.setCategory(Category.VOLLEYBALL);
        if (category.contains("баскетбол"))
            event.setCategory(Category.BASKETBALL);

        event.setAddress(eventDTO.getAddress());
        event.setCreator_id(eventDTO.getCreator_id());
        List<User> users = new ArrayList<>();
        users.add(user);
        event.setParticipants(users);
        event.setComments(new ArrayList<Comment>());
        event.setCreated(new Date());
        event.setStatus(Status.ACTIVE);
        event.setUpdated(new Date());
        events.add(event);

        user.setEvents(events);

        eventRepository.save(event);
        userRepository.save(user);

        AdminUserDto result = AdminUserDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "rem_event")
    public ResponseEntity<AdminUserDto> removeEvent(@RequestBody MiniEventDTO deleteEventDTO) {
        if (eventRepository.existsById(deleteEventDTO.getEventID()))
            eventRepository.deleteById(deleteEventDTO.getEventID());
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "subscribe")
    public ResponseEntity<AdminUserDto> subscribeOnEvent(@RequestBody MiniEventDTO subscribeEventDTO) {
        User user = userRepository.findByLogin(subscribeEventDTO.getUserName());
        Event event = eventRepository.findById(subscribeEventDTO.getEventID()).get();
        if (!user.getEvents().contains(event)) {
            user.getEvents().add(eventRepository.findById(subscribeEventDTO.getEventID()).get());
            userRepository.save(user);
        } else return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "unsubscribe")
    public ResponseEntity<AdminUserDto> unsubscribeOnEvent(@RequestBody MiniEventDTO unsubscribeEventDTO) {
        User user = userRepository.findByLogin(unsubscribeEventDTO.getUserName());
        user.getEvents().remove(eventRepository.findById(unsubscribeEventDTO.getEventID()).get());
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping(value = "change_event")
    public ResponseEntity<AdminUserDto> changeEvent(@RequestBody EventDTO eventDTO) {
        Event event = eventRepository.findById(eventDTO.getEventID()).get();

        if (eventDTO.getName() != null)
            event.setName(eventDTO.getName());
        if (eventDTO.getDescription() != null)
            event.setDescription(eventDTO.getDescription());

        String category = eventDTO.getCategory();
        if (category != null) {
            if (category.contains("футбол"))
                event.setCategory(Category.FOOTBALL);
            if (category.contains("волейбол"))
                event.setCategory(Category.VOLLEYBALL);
            if (category.contains("баскетбол"))
                event.setCategory(Category.BASKETBALL);
        }
        if (eventDTO.getAddress() != null)
            event.setAddress(eventDTO.getAddress());
        if (eventDTO.getCreator_id() != null && eventDTO.getCreator_id() > 0)
            event.setCreator_id(eventDTO.getCreator_id());

        eventRepository.save(event);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/pageable_events/{page}")
    @ResponseBody
    public Page<EventDTO> getPageableEvents(@PathVariable(value = "page") int page) {
        Page<Event> pageable = eventRepository.findPageable(PageRequest.of(page, 10));
        Page<EventDTO> map = pageable.map(EventDTO::fromEvent);
        return map;
    }

    @PostMapping(value = "get_events")
    public ResponseEntity<List<EventDTO>> getEvent() {
        List<EventDTO> collect = eventRepository.findAll().stream().map(EventDTO::fromEvent).collect(Collectors.toList());
        return new ResponseEntity<>(collect, HttpStatus.OK);
    }

    @PostMapping(value = "eventsByName")
    public ResponseEntity<List<EventDTO>> getEventsByName(@RequestBody FindByNameDto nameDto) {
        List<Event> banned = eventRepository.findByName(nameDto.getName());
        if (banned.size() < 1)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<EventDTO> collect = banned.stream().map(EventDTO::fromEvent).collect(Collectors.toList());
        return new ResponseEntity<>(collect, HttpStatus.OK);
    }

}
