package friendship_microservice;

import friendship_microservice.entity.Friendship;
import friendship_microservice.repository.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;


@SpringBootApplication
public class FriendshipApplication implements CommandLineRunner{
	
	@Autowired
	FriendshipRepository friendshipRepository;

	public static void main(String[] args) {
		SpringApplication.run(FriendshipApplication.class, args);
	}

	@Transactional
	@Override
	public void run(String... arg0) throws Exception {

		friendshipRepository.deleteAll();

		Friendship friendship = new Friendship(1,4);

		Friendship friendship1 = new Friendship(2,3);

		friendshipRepository.save(friendship);
		friendshipRepository.save(friendship1);

	}
}
