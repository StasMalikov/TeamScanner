package teamScanner.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "events")
@Entity
public class Event extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "date_event")
    private Date dateEvent;

    @Column(name = "description")
    private String description;

    @Column(name = "city")
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Column(name = "address")
    private String address;

    @Column(name = "creator_id")
    private Long creatorId;

    @ManyToMany(mappedBy = "events", fetch = FetchType.LAZY)
    private List<User> participants;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private List<Comment> comments;



    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", address='" + address + '\'' +
                ", creator_id=" + creatorId +
                '}';
    }
}
