package teamScanner.repository;

import teamScanner.model.Comment;
import teamScanner.model.Event;
import teamScanner.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findByCreatorAndEventAndCommentTextAndCreated(User creator, Event event, String commentText, Date created);

}
