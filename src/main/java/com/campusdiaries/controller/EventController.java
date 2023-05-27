package com.campusdiaries.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.campusdiaries.entity.Event;
import com.campusdiaries.entity.User;
import com.campusdiaries.service.EventService;
import com.campusdiaries.service.UserService;
import com.campusdiaries.util.FileUploadUtil;

@Controller
@RequestMapping(value = "admin/event")
public class EventController {
	private EventService eventService;
	private UserService userService;

	public EventController(EventService eventService, UserService userService) {
		this.eventService = eventService;
		this.userService = userService;
	}

	@GetMapping(value = "/index")
	public String events(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
		List<Event> events = eventService.getAllEvent();
		model.addAttribute("listEvents", events);
		model.addAttribute("keyword", keyword);
		return "admin/list/events_list";
	}

	@GetMapping(value = "/create")
	public String formEvents(Model model) {
		model.addAttribute("event", new Event());
		List<User> users = userService.getAllUser();
		model.addAttribute("listUsers", users);

		return "admin/entry/event_entry";
	}

	@GetMapping(value = "/delete/{id}")
	public String deleteEvent(@PathVariable(value = "id") Integer id, String keyword) {
		eventService.removeEvent(id);
		return "redirect:/admin/event/index?keyword=" + keyword;
	}

	@GetMapping(value = "/update/{id}")
	public String updateEvent(@PathVariable(value = "id") Integer id, Model model) {
		Event event = eventService.loadEventById(id);
		model.addAttribute("event", event);
		List<User> users = userService.getAllUser();
		model.addAttribute("listUsers", users);

		return "admin/edit/event_edit";
	}

	@PostMapping(value = "/save")
	public String save(Event event,@RequestParam("file") MultipartFile file) throws IOException {
		
	    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	    if(fileName.length()>3) {
		event.setBanner(fileName);
		eventService.createOrUpdateEvent(event);
	    String uploadDir = "assets1/images/event";
	    FileUploadUtil.saveFile(uploadDir, fileName,file);
	    }else {
		eventService.createOrUpdateEvent(event);
	    }
	    
		return "redirect:/admin/event/index";
	}

}
