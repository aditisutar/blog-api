package com.blogappapi.controllers;

import com.blogappapi.payloads.ApiResponse;
import com.blogappapi.payloads.UserDTO;
import com.blogappapi.services.UserService;
import jakarta.validation.Valid;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
        UserDTO createdUserDto=this.userService.createUser(userDTO);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
     }
    @PutMapping("/{id}")
     public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable("id") int id){
       UserDTO updatedUser= this.userService.updateUser(userDTO,id);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);

     }
     @PutMapping("/{id}/{name}")
     public ResponseEntity<UserDTO> updateUserName(@PathVariable int id, @PathVariable String name){
       UserDTO updatedUser= this.userService.updateuserName(id,name);
       return new ResponseEntity<>(updatedUser,HttpStatus.OK);
     }
     @DeleteMapping("/{id}")
     public ResponseEntity<?> deleteUser(@PathVariable("id") int id){
        this.userService.deleteUser(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted",true), HttpStatus.OK);
     }
    @GetMapping("/")
     public ResponseEntity<List<UserDTO>> getAllUsers(){
      return new ResponseEntity<>(this.userService.getAllUsers(),HttpStatus.OK);
     }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") int id){
        return new ResponseEntity<>(this.userService.getUserById(id),HttpStatus.OK);
    }
}
