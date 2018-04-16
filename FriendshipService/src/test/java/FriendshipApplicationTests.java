import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FriendshipApplicationTests {
    private final String BASE_URL = "http://localhost:8080/";


    @Test
    public void getAllFriend(){
        List<com.javasampleapproach.restdata.model.Friendship> friendships = new ArrayList<>();
    }

    @Test
    public void contextLoads() {
    }

}