package com.campusdiaries.dao;

import org.springframework.data.jpa.repository.JpaRepository; 
import com.campusdiaries.entity.User;

public interface UserDAO extends JpaRepository<User, Integer> { 

} 
