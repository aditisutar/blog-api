package com.blogappapi.services.impl;

import com.blogappapi.entities.User;
import com.blogappapi.exceptions.ResourceNotFoundException;
import com.blogappapi.payloads.UserDTO;
import com.blogappapi.repos.UserRepo;
import com.blogappapi.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServceImpl implements UserService {
   Logger logger= LoggerFactory.getLogger(UserServceImpl.class);

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Override
    public UserDTO createUser(UserDTO userDto) {
    User user=new User();
    user.setName(userDto.getName());
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    user.setAbout(userDto.getAbout());
    user.setEmail(userDto.getEmail());
    //logger.info("user password: "+user.getPassword());
   // user=this.dtoToUser(userDto);
    User savedUser= this.userRepo.save(user);

    return userToDto(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDto, int userId) {
      User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
      user.setName(userDto.getName());
      user.setEmail(userDto.getEmail());
      user.setPassword(userDto.getPassword());
      user.setAbout(userDto.getAbout());
      User updatedUser= this.userRepo.save(user);
      return  userToDto(updatedUser);
    }

    @Override
    public UserDTO updateuserName(int id, String name) {
       User u= this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User","name",name));
       u.setName(name);
       User updateduser= this.userRepo.save(u);
       return this.userToDto(updateduser);
        //return this.modelMapper.map(updateduser,UserDTO.class);
    }

    @Override
    public UserDTO getUserById(int userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));

        return userToDto(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
      List<User> user=  this.userRepo.findAll();
     List<UserDTO> userD= user.stream().map(user1 -> userToDto(user1)).collect(Collectors.toList());

        return userD ;
    }

    @Override
    public void deleteUser(int userId) {
   User user= this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
    this.userRepo.delete(user);

    }
    public User dtoToUser(UserDTO userDTO){
        User user=this.modelMapper.map(userDTO,User.class);
//        user.setId(userDTO.getId());
//        user.setEmail(userDTO.getEmail());
//        user.setName(userDTO.getName());
//        user.setPassword(userDTO.getPassword());
//        user.setAbout(userDTO.getAbout());
        return user;
    }
    public UserDTO userToDto(User user){
        UserDTO userDTO=this.modelMapper.map(user, UserDTO.class);
//        userDTO.setId(user.getId());
//        userDTO.setName(user.getName());
//        userDTO.setEmail(user.getEmail());
//        userDTO.setPassword(user.getPassword());
//        userDTO.setAbout(user.getAbout());
        return  userDTO;
    }
}
