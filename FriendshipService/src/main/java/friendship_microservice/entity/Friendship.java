package friendship_microservice.entity;

import javax.persistence.*;

@Entity
@Table(name = "friendship_tbl")
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "userId")
    private long userId;
    @Column(name = "friendId")
    private long friendId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getFriendId() {
        return friendId;
    }

    public void setFriendId(long friendId) {
        this.friendId = friendId;
    }

    public Friendship(long userId, long friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public Friendship() {

    }

    @Override
    public String toString() {
        return "Friendship{" +
                "userId=" + userId +
                ", friendId=" + friendId +
                '}';
    }
}
