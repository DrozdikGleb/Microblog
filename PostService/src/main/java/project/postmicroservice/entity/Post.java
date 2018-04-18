package project.postmicroservice.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="POST")
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="postID", nullable = false)
    private long postId;
    @Column(name="userID", nullable = false)
    private long userId;
    @Column(name="dateID", nullable = false)
    private Date date;
    @Column(name="message")
    @Type(type = "text")
    private String message;

    public Post(long userId, String message) {
        this.userId = userId;
        this.date = new Date();
        this.message = message;
    }

    public Post() {

    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", userId=" + userId +
                ", date=" + date +
                ", message='" + message + '\'' +
                '}';
    }
}
