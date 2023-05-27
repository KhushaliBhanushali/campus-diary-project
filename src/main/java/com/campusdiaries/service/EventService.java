package com.campusdiaries.service;

import com.campusdiaries.entity.Event;
import java.util.List;

public interface EventService { 

  List<Event> getAllEvent();

Event loadEventById(Integer id );

Event createOrUpdateEvent(Event event);

void removeEvent(Integer id);

} 
