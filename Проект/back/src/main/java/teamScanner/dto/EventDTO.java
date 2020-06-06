package teamScanner.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import teamScanner.model.Event;
import teamScanner.repository.UserRepository;
import teamScanner.service.EntityManagerService;
import teamScanner.service.UserService;
import teamScanner.service.impl.UserServiceImpl;

import java.util.Date;

@Data
@Service
public class EventDTO {
//    @Value("${spring.datasource.url}")
//    String DATABASE_URL;
//
//    @Value("${spring.datasource.username}")
//    String DATABASE_USER;
//
//    @Value("${spring.datasource.password}")
//    String DATABASE_PASS;
//    private static EntityManagerService ems;
//    @Autowired
//    public static EntityManagerService ems;

    //    private static UserRepository userService;
    private Long eventID;
    private String name;
    private String nameCreator;
    private String description;
    private String category;
    private String address;
    private String city;
    private Long creator_id;
    private Date dateEvent;
    private int participantsCount;

    public static EventDTO fromEvent(Event event) {
        EntityManagerService ems = new EntityManagerService();
        EventDTO eventDTO = new EventDTO();
        eventDTO.setEventID(event.getId());
        eventDTO.setName(event.getName());
        eventDTO.setNameCreator(ems.getLoginById(event.getCreatorId()));
        eventDTO.setDescription(event.getDescription());
        eventDTO.setCategory(event.getCategory().toString());
        eventDTO.setAddress(event.getAddress());
        eventDTO.setCreator_id(event.getCreatorId());
        eventDTO.setDateEvent(event.getDateEvent());
        eventDTO.setCity(event.getCity());
        eventDTO.setParticipantsCount(event.getParticipants().size());
        return eventDTO;
    }
}
