package com.blogappapi.services;

import com.blogappapi.entities.User;
import com.blogappapi.payloads.UserDTO;
import jdk.dynalink.linker.LinkerServices;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO user);
    UserDTO updateUser(UserDTO user, int userId);
    UserDTO updateuserName(int id,String name);
    UserDTO getUserById(int userId);
    List<UserDTO> getAllUsers();
    void deleteUser(int userId);
}
