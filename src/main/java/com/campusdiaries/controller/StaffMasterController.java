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

import com.campusdiaries.entity.StaffMaster;
import com.campusdiaries.entity.User;
import com.campusdiaries.service.StaffMasterService;
import com.campusdiaries.service.UserService;
import com.campusdiaries.util.FileUploadUtil;

@Controller
@RequestMapping(value = "admin/staffMaster")
public class StaffMasterController {
	private StaffMasterService staffMasterService;
	private UserService userService;

	public StaffMasterController(StaffMasterService staffMasterService, UserService userService) {
		this.staffMasterService = staffMasterService;
		this.userService = userService;
	}

	@GetMapping(value = "/index")
	public String staffMasters(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
		List<StaffMaster> staffMasters = staffMasterService.getAllStaffMaster();
		model.addAttribute("listStaffMasters", staffMasters);
		model.addAttribute("keyword", keyword);
		return "admin/list/staffMasters_list";
	}

	@GetMapping(value = "/create")
	public String formStaffMasters(Model model) {
		model.addAttribute("staffMaster", new StaffMaster());
		List<User> users = userService.getAllUser();
		model.addAttribute("listUsers", users);

		return "admin/entry/staffMaster_entry";
	}

	@GetMapping(value = "/delete/{id}")
	public String deleteStaffMaster(@PathVariable(value = "id") Integer id, String keyword) {
		staffMasterService.removeStaffMaster(id);
		return "redirect:/admin/staffMaster/index?keyword=" + keyword;
	}

	@GetMapping(value = "/update/{id}")
	public String updateStaffMaster(@PathVariable(value = "id") Integer id, Model model) {
		StaffMaster staffMaster = staffMasterService.loadStaffMasterById(id);
		model.addAttribute("staffMaster", staffMaster);
		List<User> users = userService.getAllUser();
		model.addAttribute("listUsers", users);

		return "admin/edit/staffMaster_edit";
	}

	@PostMapping(value = "/save")
	public String save(StaffMaster staffMaster,@RequestParam("file") MultipartFile file) throws IOException {
		
	    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	    if(fileName.length()>3) {
		staffMaster.setPhoto(fileName);
		staffMasterService.createOrUpdateStaffMaster(staffMaster);
	    String uploadDir = "assets1/images/staffs";
	    FileUploadUtil.saveFile(uploadDir, fileName,file);
	    }else {
		staffMasterService.createOrUpdateStaffMaster(staffMaster);
	    }
	    
		return "redirect:/admin/staffMaster/index";
	}

}
