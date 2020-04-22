package teamScanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamScanner.dto.AdminUserDto;
import teamScanner.dto.CommentEventDTO;
import teamScanner.model.Comment;
import teamScanner.model.Event;
import teamScanner.model.Status;
import teamScanner.model.User;
import teamScanner.repository.CommentRepository;
import teamScanner.repository.EventRepository;
import teamScanner.repository.UserRepository;

import java.util.Date;

@RestController
@RequestMapping(value = "/api/v1/comments/")
public class CommentController {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentController(UserRepository userRepository, EventRepository eventRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.commentRepository = commentRepository;
    }

    @PostMapping(value = "add_comment")
    public ResponseEntity<AdminUserDto> addComment(@RequestBody CommentEventDTO commentEventDTO) {
        User user = userRepository.findByLogin(commentEventDTO.getUserName());
        Event event = eventRepository.findById(commentEventDTO.getEventID()).get();

        Comment comment = new Comment();
        comment.setCreated(new Date());
        comment.setCreator(user);
        comment.setEvent(event);
        comment.setStatus(Status.ACTIVE);
        comment.setUpdated(new Date());
        comment.setCommentText(commentEventDTO.getComment());
        commentRepository.save(comment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "rem_comment")
    public ResponseEntity<AdminUserDto> removeComment(@RequestBody CommentEventDTO commentEventDTO) {
        if (commentRepository.existsById(commentEventDTO.getCommentID()))
            commentRepository.deleteById(commentEventDTO.getCommentID());
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
