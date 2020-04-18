package teamScanner.dto;

import lombok.Data;
import teamScanner.model.Event;

@Data
public class EventDTO {
    private Long eventID;
    private String name;
    private String description;
    private String category;
    private String address;
    private Long creator_id;

    public static EventDTO fromEvent(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setEventID(event.getId());
        eventDTO.setName(event.getName());
        eventDTO.setDescription(event.getDescription());
        eventDTO.setCategory(event.getCategory().name());
        eventDTO.setAddress(event.getAddress());
        eventDTO.setCreator_id(event.getCreator_id());

        return eventDTO;
    }
}
