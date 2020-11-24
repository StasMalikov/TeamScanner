package teamScanner.model;

import lombok.Data;

import javax.persistence.*;

/**
 * the entity that each individual comment represents
 */
@Data
@Table(name = "comments")
@Entity
public class Comment extends BaseEntity {

    /**
     * comment creator
     */
    @ManyToOne
    @JoinColumn(name = "user_creator", nullable = false)
    private User creator;

    /**
     * the event in which the comment was created
     */
    @ManyToOne
    @JoinColumn(name = "event", nullable = false)
    private Event event;

    /**
     * comment text
     */
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
