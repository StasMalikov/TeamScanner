package teamScanner.repository;

import teamScanner.model.Status;
import teamScanner.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;



public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String name);
    List<User> findByStatus(Status status);

    @Query("SELECT p FROM User p order by p.created desc")
    Page<User> findPageable(Pageable page);
}
