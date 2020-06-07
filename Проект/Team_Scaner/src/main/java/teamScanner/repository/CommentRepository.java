package teamScanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamScanner.model.Comment;


public interface CommentRepository extends JpaRepository<Comment, Long> {

//    Comment findByCreatorAndEventAndCommentTextAndCreated(User creator, Event event, String commentText, Date created);

}
