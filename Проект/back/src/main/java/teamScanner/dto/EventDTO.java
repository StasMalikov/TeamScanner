package teamScanner.dto;

import lombok.Data;
import teamScanner.model.Event;

import java.util.Date;

@Data
public class EventDTO {
    private Long eventID;
    private String name;
    private String description;
    private String category;
    private String address;
    private String city;
    private Long creator_id;
    private Date dateEvent;
    private int participantsCount;

    public static EventDTO fromEvent(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setEventID(event.getId());
        eventDTO.setName(event.getName());
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
