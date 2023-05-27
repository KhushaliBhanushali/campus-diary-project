package com.campusdiaries.service;

import com.campusdiaries.entity.User;
import java.util.List;

public interface UserService { 

  List<User> getAllUser();

User loadUserById(Integer id );

User createOrUpdateUser(User user);

void removeUser(Integer id);

} 
