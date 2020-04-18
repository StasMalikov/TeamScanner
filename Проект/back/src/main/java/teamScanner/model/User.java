package teamScanner.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User extends BaseEntity {
    @Column(name = "login",unique = true)
    private String login;

//    @Column(name = "first_name")
//    private String firstName;

//    @Column(name = "last_name")
//    private String lastName;

//    @Column(name = "email")
//    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    @Column(name = "city")
    private String city;

    @Column(name = "age")
    private int age;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_event",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "event_id",referencedColumnName = "id")
    )
    private List<Event> events;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private List<Comment> comments;

}
