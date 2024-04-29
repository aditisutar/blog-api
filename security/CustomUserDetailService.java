//package com.blogappapi.security;
//
//import com.blogappapi.entities.User;
//import com.blogappapi.exceptions.ResourceNotFoundException;
//import com.blogappapi.repos.UserRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomUserDetailService implements UserDetailsService {
//    @Autowired
//    private UserRepo userRepo;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    User user=this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("Username","email",username));
//        return user;
//    }
//}
