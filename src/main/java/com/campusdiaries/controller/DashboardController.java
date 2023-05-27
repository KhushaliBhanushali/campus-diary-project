package com.campusdiaries.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.campusdiaries.service.EventRegistrationService;
import com.campusdiaries.service.EventService;
import com.campusdiaries.service.PostService;
import com.campusdiaries.service.StaffMasterService;
import com.campusdiaries.service.StudentMasterService;


@Controller
@RequestMapping(value = "/admin")
public class DashboardController {
	StudentMasterService  studentMasterService;
	StaffMasterService staffMasterService;
	EventRegistrationService eventRegistrationService;
	EventService eventService;
	
	public DashboardController(StudentMasterService studentMasterService, StaffMasterService staffMasterService,
		EventRegistrationService eventRegistrationService, EventService eventService) {
	    super();
	    this.studentMasterService = studentMasterService;
	    this.staffMasterService = staffMasterService;
	    this.eventRegistrationService = eventRegistrationService;
	    this.eventService = eventService;
	}
	
	@GetMapping(value = "/admin")
	public String getDashboard(Model m) {
		
		int s_count =  studentMasterService.getAllStudentMaster().size();
		int t_count =  staffMasterService.getAllStaffMaster().size();
		int r_count =  eventRegistrationService.getAllEventRegistration().size();
		int e_count =  eventService.getAllEvent().size();
		m.addAttribute("student_count",s_count);
		m.addAttribute("staff_count",t_count);
		m.addAttribute("registration_count",r_count);
		m.addAttribute("event_count",e_count);
		
		return "admin/dashboard";
	}



	
}
