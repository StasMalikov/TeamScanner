package teamScanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamScanner.model.Comment;
import teamScanner.model.Event;
import teamScanner.model.User;

import java.util.Date;


public interface CommentRepository extends JpaRepository<Comment, Long> {
}
