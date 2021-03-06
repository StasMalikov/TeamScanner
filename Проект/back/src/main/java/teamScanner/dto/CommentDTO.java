package teamScanner.dto;

import lombok.Data;
import teamScanner.model.Comment;

@Data
public class CommentDTO {
    private Long creator;
    private String userName;
    private Long event;
    private String commentText;


    public static CommentDTO fromComment(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCreator(comment.getCreator().getId());
        commentDTO.setUserName(comment.getCreator().getLogin());
        commentDTO.setEvent(comment.getEvent().getId());
        commentDTO.setCommentText(comment.getCommentText());
        return commentDTO;
    }
}
