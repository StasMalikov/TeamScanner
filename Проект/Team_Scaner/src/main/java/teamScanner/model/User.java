package teamScanner.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
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

    public List<String> getRolesStr() {
        List<String> list = new ArrayList<>();
        roles.forEach(role -> list.add(role.getName()));
        return list;
    }

    @Column(name = "city")
    private String city;

    @Column(name = "age")
    private Date age;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_event",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "event_id",referencedColumnName = "id")
    )
    private List<Event> events;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", city='" + city + '\'' +
                ", age=" + age +
                '}';
    }
}
