import com.fasterxml.jackson.databind.ObjectMapper;
import friendship_microservice.FriendshipApplication;
import friendship_microservice.entity.Friendship;
import friendship_microservice.repository.FriendshipRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = FriendshipApplication.class)
@WebAppConfiguration
public class FriendshipApplicationTests {

    private MockMvc mockMvc;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build();

        friendshipRepository.deleteAll();
        friendshipRepository.save(new Friendship(1, 4));
        friendshipRepository.save(new Friendship(2, 3));
        friendshipRepository.save(new Friendship(3, 4));
        friendshipRepository.save(new Friendship(3, 2));
    }

    @Test
    public void createFriendshipBetween1And5() throws Exception {
        String friendshipJson = asJsonString(new Friendship(1, 5));

        mockMvc.perform(post("/friendship/{user_id}/{friend_id}", 1, 5)
                .contentType(contentType)
                .content(friendshipJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteFriendshipBetween2And3() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/friendship/{user_id}/{friend_id}", 2, 3)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteNonexistentFriendship() throws Exception {
        mockMvc.perform(delete("/friendship/{user_id}/{friend_id}", 100, 500))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getFriendListOfNonexistentUser() throws Exception {
        mockMvc.perform(get("/friendship/{user_id}", 100))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getFriendListForUserWithId3() throws Exception {
        List<Long> friendsId = Arrays.asList(4L, 2L);
        mockMvc.perform(get("/friendship/{user_id}", 3)
                .contentType(contentType)
                .content(asJsonString(friendsId))).andExpect(status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}