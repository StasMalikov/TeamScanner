package teamScanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import teamScanner.dto.AdminUserDto;
import teamScanner.dto.CommentDTO;
import teamScanner.dto.CommentEventDTO;
import teamScanner.dto.EventDTO;
import teamScanner.model.Comment;
import teamScanner.model.Event;
import teamScanner.model.Status;
import teamScanner.model.User;
import teamScanner.repository.CommentRepository;
import teamScanner.repository.EventRepository;
import teamScanner.repository.UserRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

//    @PostMapping(value = "rem_comment")
//    public ResponseEntity<AdminUserDto> getCommentInEvent(@RequestBody CommentEventDTO commentEventDTO) {
//        if (commentRepository.existsById(commentEventDTO.getCommentID()))
//            commentRepository.deleteById(commentEventDTO.getCommentID());
//        else
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @Transactional
    @GetMapping(value = "get_comments_in_event/{id}")
    public ResponseEntity<List<CommentDTO>> getCommentInEvent(@PathVariable(value = "id") Long id) {
        Event event1 = new Event();
        if (eventRepository.existsById(id))
            event1 = eventRepository.findById(id).get();
        List<Comment> comments = event1.getComments();

        List<CommentDTO> collect1 = comments.stream().map(CommentDTO::fromComment).collect(Collectors.toList());
//        List<Long> idEventsWhereUserExist = entityManagerService.getIdEventsWhereUserExist(id);
//        List<Comment> collect = new ArrayList<>();
//        for (Long integer : idEventsWhereUserExist) {
//            Event event = eventRepository.findById(integer).get();
//            if (event != null)
//                collect.add(event);
//        }
//        List<CommentDTO> collect1 = collect.stream().map(CommentDTO::fromComment).collect(Collectors.toList());
        if (collect1.size() > 0)
            return new ResponseEntity<>(collect1, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
