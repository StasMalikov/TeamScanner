package teamScanner.repository;

import teamScanner.model.Event;
import teamScanner.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    List<Event> findByName(String name);

    List<Event> findByStatus(Status status);

    @Query("SELECT p FROM Event p order by p.created desc")
    Page<Event> findPageable(Pageable page);



//    @Query("SELECT p " +
//            "FROM Event p " +
//            "where  p.name IN :matrix and" +
//            "       p.screenResolution IN :resolution and" +
//            "       p.price >= :minp and p.price <= :maxp and" +
//            "       p.screen >= :minScreen and p.screen <=:maxScreen and" +
//            "       p.screenFrequency >= :miff and p.screenFrequency <= :maff"
//    )
//    Page<Monitor> findAllbyParams(@Param("minp") BigDecimal minPrice,
//                                  @Param("maxp") BigDecimal maxPrice,
//                                  @Param("miff") int minf,
//                                  @Param("maff") int maxf,
//                                  Double minScreen,
//                                  Double maxScreen,
//                                  Collection<String> matrix,
//                                  Collection<String> resolution,
//                                  Pageable page
//    );
}
