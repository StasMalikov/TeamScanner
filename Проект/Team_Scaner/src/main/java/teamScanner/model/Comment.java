package teamScanner.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "comments")
@Entity
public class Comment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_creator", nullable = false)
    private User creator;

    @ManyToOne
    @JoinColumn(name = "event", nullable = false)
    private Event event;

    @Column(name = "comment_text")
    private String commentText;

    @Override
    public String toString() {
        return "Comment{" +
                "creator=" + creator.getLogin() +
                ", event=" + event.getName() +
                ", commentText='" + commentText + '\'' +
                '}';
    }
}
