package friendship_microservice.entity;

import javax.persistence.*;

/**
 * Created by Gleb on 15.04.2018
 */
@Entity
@Table(name = "friendship_tbl")
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "user_id")
    private long user_id;
    @Column(name = "friend_id")
    private long friend_id;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(long friend_id) {
        this.friend_id = friend_id;
    }

    public Friendship(long user_id, long friend_id) {
        this.user_id = user_id;
        this.friend_id = friend_id;
    }

    public Friendship(){

    }

    @Override
    public String toString() {
        return "Friendship{" +
                "user_id=" + user_id +
                ", friend_id=" + friend_id +
                '}';
    }
}
