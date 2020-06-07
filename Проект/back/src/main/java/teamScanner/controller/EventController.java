package teamScanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import teamScanner.dto.*;
import teamScanner.model.*;
import teamScanner.repository.CommentRepository;
import teamScanner.repository.EventRepository;
import teamScanner.repository.SearchSpecification;
import teamScanner.repository.UserRepository;
import teamScanner.service.EntityManagerService;
import teamScanner.service.UserService;

import java.util.*;
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


    //    @Transactional
//    @GetMapping(value = "get_subscribe/{id}")
//    public ResponseEntity<List<EventDTO>> getSubscribe(@PathVariable(value = "id") Long id) {
    @Transactional
    @PostMapping(value = "get_subscribe")
    public ResponseEntity<String> getSubscribe(@RequestBody MiniEventDTO subscribeEventDTO) {
        Event event = new Event();
        if (eventRepository.existsById(subscribeEventDTO.getEventID())) {
            event = eventRepository.findById(subscribeEventDTO.getEventID()).get();
        } else return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        List<User> participants = event.getParticipants();
        boolean contains = participants.contains(userRepository.findByLogin(subscribeEventDTO.getUserName()));
        return new ResponseEntity<>(String.valueOf(contains), HttpStatus.OK);
    }

    @Transactional
    @PostMapping(value = "add_event")
    public ResponseEntity<String> addEvent(@RequestBody EventDTO eventDTO) {
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
        if (category.toLowerCase().contains("хоккей"))
            event.setCategory(Category.HOCKEY);

        event.setAddress(eventDTO.getAddress());
        event.setCreatorId(eventDTO.getCreator_id());
        List<User> users = new ArrayList<>();
        users.add(user);
        event.setParticipants(users);
        event.setComments(new ArrayList<Comment>());
        event.setCreated(new Date());
        event.setStatus(Status.ACTIVE);
        event.setUpdated(new Date());
        event.setCity(eventDTO.getCity());
        event.setDateEvent(eventDTO.getDateEvent());
        events.add(event);
        event.setCity(eventDTO.getCity());

        user.setEvents(events);

        eventRepository.save(event);
        userRepository.save(user);

        //AdminUserDto result = AdminUserDto.fromUser(user);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @Transactional
    @PostMapping(value = "rem_event")
    public ResponseEntity<AdminUserDto> removeEvent(@RequestBody MiniEventDTO deleteEventDTO) {
        if (eventRepository.existsById(deleteEventDTO.getEventID())) {
            Event event = eventRepository.findById(deleteEventDTO.getEventID()).get();
            List<User> participants = event.getParticipants();
            for (User participant : participants) {
                List<Event> events = participant.getEvents();
                events.remove(event);
                participant.setEvents(events);
                userRepository.save(participant);
            }
            event.setParticipants(null);
            eventRepository.deleteById(deleteEventDTO.getEventID());
        } else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
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

    @Transactional
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
            if (category.contains("Футбол"))
                event.setCategory(Category.FOOTBALL);
            if (category.contains("Волейбол"))
                event.setCategory(Category.VOLLEYBALL);
            if (category.contains("Баскетбол"))
                event.setCategory(Category.BASKETBALL);
            if (category.toLowerCase().contains("хоккей"))
                event.setCategory(Category.HOCKEY);
        }
        if (eventDTO.getAddress() != null)
            event.setAddress(eventDTO.getAddress());
        if (eventDTO.getCreator_id() != null && eventDTO.getCreator_id() > 0)
            event.setCreatorId(eventDTO.getCreator_id());

        if (eventDTO.getDateEvent() != null)
            event.setDateEvent(eventDTO.getDateEvent());

        if (eventDTO.getCity() != null)
            event.setCity(eventDTO.getCity());

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

    @Transactional
    @PostMapping(value = "get_events")
    public ResponseEntity<List<EventDTO>> getEvent() {
        List<Event> collect = eventRepository.findAll();
        List<EventDTO> collect1 = collect.stream().map(EventDTO::fromEvent).collect(Collectors.toList());
        return new ResponseEntity<>(collect1, HttpStatus.OK);
    }

    @Transactional
    @GetMapping(value = "get_creator_events/{id}")
    public ResponseEntity<List<EventDTO>> getCreatorEvent(@PathVariable(value = "id") Long id) {
        List<Event> collect = eventRepository.findByCreatorId(id);
        List<EventDTO> collect1 = collect.stream().map(EventDTO::fromEvent).collect(Collectors.toList());

        return new ResponseEntity<>(collect1, HttpStatus.OK);
    }

    @Autowired
    EntityManagerService entityManagerService;

    @Transactional
    @GetMapping(value = "get_events_where_user_exist/{id}")
    public ResponseEntity<List<EventDTO>> getUserExistEvent(@PathVariable(value = "id") Long id) {
        List<Long> idEventsWhereUserExist = entityManagerService.getIdEventsWhereUserExist(id);
        List<Event> collect = new ArrayList<>();
        for (Long integer : idEventsWhereUserExist) {
            Event event = eventRepository.findById(integer).get();
            if (event != null)
                collect.add(event);
        }
        List<EventDTO> collect1 = collect.stream().map(EventDTO::fromEvent).collect(Collectors.toList());

        return new ResponseEntity<>(collect1, HttpStatus.OK);
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
    @PostMapping(value = "sort_events")
    public ResponseEntity<List<EventDTO>> getSortedEvents(@RequestBody EventDTO eventDTO) {
        Map<String, Object> map = new HashMap<>();
        if (eventDTO.getName() != null && eventDTO.getName() != "")
            map.put("name", eventDTO.getName());
        if (eventDTO.getCategory() != null && eventDTO.getCategory() != "") {
            String category = eventDTO.getCategory().toLowerCase();
            if (category.contains("футбол"))
                map.put("category", Category.FOOTBALL);
            if (category.contains("волейбол"))
                map.put("category", Category.VOLLEYBALL);
            if (category.contains("баскетбол"))
                map.put("category", Category.BASKETBALL);
            if (category.toLowerCase().contains("хоккей"))
                map.put("category", Category.HOCKEY);
        }
        if (eventDTO.getAddress() != null && eventDTO.getAddress() != "")
            map.put("address", eventDTO.getAddress());

        if (eventDTO.getCity() != null && eventDTO.getCity() != "")
            map.put("city", eventDTO.getCity());

        SearchSpecification search = new SearchSpecification(map);

        List<Event> all = eventRepository.findAll(Specification.where(search));


        if (eventDTO.getDateEvent() != null) {
            String now = eventDTO.getDateEvent().getYear() + " " + eventDTO.getDateEvent().getMonth() + " " + eventDTO.getDateEvent().getDay();
            List<Event> allD = new ArrayList<>();
            all.forEach(p -> {
                String nowP = p.getDateEvent().getYear() + " " + p.getDateEvent().getMonth() + " " + p.getDateEvent().getDay();
                if (now.equals(nowP))
                    allD.add(p);
            });
            List<EventDTO> collect = allD.stream().map(EventDTO::fromEvent).collect(Collectors.toList());
            return new ResponseEntity<>(collect, HttpStatus.OK);
        }
        List<EventDTO> collect = all.stream().map(EventDTO::fromEvent).collect(Collectors.toList());
        return new ResponseEntity<>(collect, HttpStatus.OK);
    }

}
