package teamScanner.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;


/**
 * parent class of all entities
 * stores records such as
 * id, created time ,updated time ,enum status {ACTIVE, NOT_ACTIVE, BANNED}
 */
@MappedSuperclass
@Data
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreatedDate
    @Column(name = "created")
    private Date created;

    @LastModifiedDate
    @Column(name = "updated")
    private Date updated;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}
