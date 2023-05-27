package com.campusdiaries.dao;

import org.springframework.data.jpa.repository.JpaRepository; 
import com.campusdiaries.entity.Event;

public interface EventDAO extends JpaRepository<Event, Integer> { 

} 
