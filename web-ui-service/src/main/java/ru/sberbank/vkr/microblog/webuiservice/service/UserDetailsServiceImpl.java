package ru.sberbank.vkr.microblog.webuiservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ru.sberbank.vkr.microblog.webuiservice.entity.AppUser;
import ru.sberbank.vkr.microblog.webuiservice.entity.UserPrincipal;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final ProfileExchangeService profileExchangeService;

    @Autowired
    public UserDetailsServiceImpl(ProfileExchangeService profileExchangeService) {
        this.profileExchangeService = profileExchangeService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {
        if (userName.equals("")) {
            logger.debug("Empty user name for authorization");
            throw new UsernameNotFoundException("User name cannot be empty");
        }

        UserPrincipal user = this.profileExchangeService.findUserAccount(userName);

        if (user == null) {
            logger.debug("User {} not found!", userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }

        logger.debug("Found user: {}", user);

        List<GrantedAuthority> grantList = new ArrayList<>();
        grantList.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new AppUser(user.getLogin(), user.getPassword(), grantList, user.getId());
    }
}