package com.campusdiaries.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.campusdiaries.entity.Notification;
import com.campusdiaries.entity.User;
import com.campusdiaries.service.NotificationService;
import com.campusdiaries.service.UserService;

@Controller
@RequestMapping(value = "admin/notification")
public class NotificationController {
	private NotificationService notificationService;
	private UserService userService;
	
	public NotificationController(NotificationService notificationService, UserService userService
			) {
		this.notificationService = notificationService;
		
		this.userService = userService;
	}

	@GetMapping(value = "/index")
	public String notifications(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
		List<Notification> notifications = notificationService.getAllNotification();
		model.addAttribute("listNotifications", notifications);
		model.addAttribute("keyword", keyword);
		return "admin/list/notifications_list";
	}

	@GetMapping(value = "/create")
	public String formNotifications(Model model) {
		model.addAttribute("notification", new Notification());
		List<User> users = userService.getAllUser();
		model.addAttribute("listUsers", users);

		

		return "admin/entry/notification_entry";
	}

	@GetMapping(value = "/delete/{id}")
	public String deleteNotification(@PathVariable(value = "id") Integer id, String keyword) {
		notificationService.removeNotification(id);
		return "redirect:/admin/notification/index?keyword=" + keyword;
	}

	@GetMapping(value = "/update/{id}")
	public String updateNotification(@PathVariable(value = "id") Integer id, Model model) {
		Notification notification = notificationService.loadNotificationById(id);
		model.addAttribute("notification", notification);
		List<User> users = userService.getAllUser();
		model.addAttribute("listUsers", users);

		return "admin/edit/notification_edit";
	}

	@PostMapping(value = "/save")
	public String save(Notification notification) {
		notificationService.createOrUpdateNotification(notification);
		return "redirect:/admin/notification/index";
	}

}
