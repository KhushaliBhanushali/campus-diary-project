package com.campusdiaries.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.campusdiaries.entity.Event;
import com.campusdiaries.entity.EventRegistration;
import com.campusdiaries.entity.User;
import com.campusdiaries.service.EventRegistrationService;
import com.campusdiaries.service.EventService;
import com.campusdiaries.service.UserService;

@Controller
@RequestMapping(value = "admin/eventRegistration")
public class EventRegistrationController {
    private EventRegistrationService eventRegistrationService;
    private EventService eventService;
    private UserService userService;

    public EventRegistrationController(EventRegistrationService eventRegistrationService, EventService eventService,
	    UserService userService) {
	this.eventRegistrationService = eventRegistrationService;
	this.eventService = eventService;
	this.userService = userService;
    }

    @GetMapping(value = "/index")
    public String eventRegistrations(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
	List<EventRegistration> eventRegistrations = eventRegistrationService.getAllEventRegistration();
	model.addAttribute("listEventRegistrations", eventRegistrations);
	model.addAttribute("keyword", keyword);
	return "admin/list/eventRegistrations_list";
    }

    @GetMapping(value = "/create")
    public String formEventRegistrations(Model model) {
	model.addAttribute("eventRegistration", new EventRegistration());
	List<Event> events = eventService.getAllEvent();
	model.addAttribute("listEvents", events);

	List<User> users = userService.getAllUser();
	model.addAttribute("listUsers", users);

	return "admin/entry/eventRegistration_entry";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteEventRegistration(@PathVariable(value = "id") Integer id, String keyword) {
	eventRegistrationService.removeEventRegistration(id);
	return "redirect:/admin/eventRegistration/index?keyword=" + keyword;
    }

    @GetMapping(value = "/update/{id}")
    public String updateEventRegistration(@PathVariable(value = "id") Integer id, Model model) {
	EventRegistration eventRegistration = eventRegistrationService.loadEventRegistrationById(id);
	model.addAttribute("eventRegistration", eventRegistration);
	List<Event> events = eventService.getAllEvent();
	model.addAttribute("listEvents", events);

	List<User> users = userService.getAllUser();
	model.addAttribute("listUsers", users);

	return "admin/edit/eventRegistration_edit";
    }

    @PostMapping(value = "/save")
    public String save(EventRegistration eventRegistration) {
	eventRegistrationService.createOrUpdateEventRegistration(eventRegistration);
	return "redirect:/admin/eventRegistration/index";
    }

}
