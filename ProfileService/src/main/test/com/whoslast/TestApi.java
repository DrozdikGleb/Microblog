package com.whoslast;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.whoslast.entities.User;

public class TestApi {

	private static final String REST_SERVICE_URI = "http://localhost:8080";
	
	/* GET */
	@SuppressWarnings("unchecked")
	private static void listAllUsers(){
		System.out.println("Testing listAllUsers API-----------");
		
		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap<String, Object>> usersMap = restTemplate.getForObject(REST_SERVICE_URI+"/user/", List.class);
		
		if(usersMap!=null){
			for(LinkedHashMap<String, Object> map : usersMap){
	            System.out.println("User : id= "+map.get("id")+", name = "+map.get("firstName"));
	        }
		}else{
			System.out.println("No user exist----------");
		}
	}
	
	/* GET */
	private static void getUser(){
		System.out.println("Testing getUser API----------");
		RestTemplate restTemplate = new RestTemplate();
        User user = restTemplate.getForObject(REST_SERVICE_URI+"/user/1", User.class);
        System.out.println(user);
	}

	private static void getUserByName(){
		System.out.println("Testing getUserByName API----------");
		RestTemplate restTemplate = new RestTemplate();
		User user = restTemplate.getForObject(REST_SERVICE_URI+"/userauth/login", User.class);
		System.out.println(user);
	}
	
	/* POST */
    private static void createUser() {
		System.out.println("Testing create User API----------");
    	RestTemplate restTemplate = new RestTemplate();
        User user = new User("newLogin", "qwe", "asdf@mail.com", "brenda", "watkins", 1);
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/user/", user, User.class);
        System.out.println("Location : "+uri.toASCIIString());
    }

    /* PUT */
    private static void updateUser() {
		System.out.println("Testing update User API----------");
        RestTemplate restTemplate = new RestTemplate();
		User user = new User("lll", "qwe", "a123f@mail.com", "michael", "lauren", 1);
        restTemplate.put(REST_SERVICE_URI+"/user/1", user);
        System.out.println(user);
    }

    /* DELETE */
    private static void deleteUser() {
		System.out.println("Testing delete User API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI+"/user/3");
    }


    /* DELETE */
    private static void deleteAllUsers() {
		System.out.println("Testing all delete Users API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI+"/user/");
    }

    /* Test population */
	private static void populate(){
		System.out.println("Testing create User API----------");
		RestTemplate restTemplate = new RestTemplate();
		User user;
		URI uri;
		user = new User("login", "qwe", "asdf@mail.com", "brenda", "watkins", 1);
		uri = restTemplate.postForLocation(REST_SERVICE_URI+"/user/", user, User.class);
		System.out.println("Location : "+uri.toASCIIString());

		user = new User("asd", "qwe", "qqq@mail.com", "dp", "floiro", 2);
		uri = restTemplate.postForLocation(REST_SERVICE_URI+"/user/", user, User.class);
		System.out.println("Location : "+uri.toASCIIString());

		user = new User("zxc", "qwe", "www@mail.com", "avg5tg", "borkok", 5);
		uri = restTemplate.postForLocation(REST_SERVICE_URI+"/user/", user, User.class);
		System.out.println("Location : "+uri.toASCIIString());

		user = new User("ert", "qwe", "eee@mail.com", "rrre", "qqwewe", 7);
		uri = restTemplate.postForLocation(REST_SERVICE_URI+"/user/", user, User.class);
		System.out.println("Location : "+uri.toASCIIString());
	}

    public static void main(String args[]){
		populate();
		getUserByName();
		listAllUsers();

		getUser();
		createUser();
		listAllUsers();
		updateUser();
		listAllUsers();
		deleteUser();
		listAllUsers();
		deleteAllUsers();
		listAllUsers();
    }
}