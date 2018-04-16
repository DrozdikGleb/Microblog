import friendship_microservice.entity.Friendship;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FriendshipApplicationTests {
    private final String BASE_URL = "http://localhost:8080/";


    @Test
    public void getAllFriend(){
        List<Friendship> friendships = new ArrayList<>();
    }

    @Test
    public void contextLoads() {
    }

}