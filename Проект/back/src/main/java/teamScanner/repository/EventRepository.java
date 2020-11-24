package teamScanner.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teamScanner.model.Event;
import teamScanner.model.Status;

import java.util.List;
import java.util.Locale;


public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    List<Event> findByName(String name);

    List<Event> findByStatus(Status status);

    @Query("SELECT p FROM Event p order by p.created desc")
    Page<Event> findPageable(Pageable page);

    List<Event> findByCreatorId(Long id);
}
