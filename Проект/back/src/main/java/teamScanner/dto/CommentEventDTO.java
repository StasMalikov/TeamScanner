package teamScanner.dto;

import lombok.Data;

@Data
public class CommentEventDTO {
    String userName;
    String comment;
    Long commentID;
    Long eventID;
}
