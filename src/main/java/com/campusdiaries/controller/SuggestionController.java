package com.campusdiaries.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.campusdiaries.entity.Suggestion;
import com.campusdiaries.entity.User;
import com.campusdiaries.service.SuggestionService;
import com.campusdiaries.service.UserService;

@Controller
@RequestMapping(value = "admin/suggestion")
public class SuggestionController {
	private SuggestionService suggestionService;
	private UserService userService;

	public SuggestionController(SuggestionService suggestionService, UserService userService) {
		this.suggestionService = suggestionService;
		this.userService = userService;
	}

	@GetMapping(value = "/index")
	public String suggestions(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
		List<Suggestion> suggestions = suggestionService.getAllSuggestion();
		model.addAttribute("listSuggestions", suggestions);
		model.addAttribute("keyword", keyword);
		return "admin/list/suggestions_list";
	}

	@GetMapping(value = "/create")
	public String formSuggestions(Model model) {
		model.addAttribute("suggestion", new Suggestion());
		List<User> users = userService.getAllUser();
		model.addAttribute("listUsers", users);

		return "admin/entry/suggestion_entry";
	}

	@GetMapping(value = "/delete/{id}")
	public String deleteSuggestion(@PathVariable(value = "id") Integer id, String keyword) {
		suggestionService.removeSuggestion(id);
		return "redirect:/admin/suggestion/index?keyword=" + keyword;
	}

	@GetMapping(value = "/update/{id}")
	public String updateSuggestion(@PathVariable(value = "id") Integer id, Model model) {
		Suggestion suggestion = suggestionService.loadSuggestionById(id);
		model.addAttribute("suggestion", suggestion);
		List<User> users = userService.getAllUser();
		model.addAttribute("listUsers", users);

		return "admin/edit/suggestion_edit";
	}

	@PostMapping(value = "/save")
	public String save(Suggestion suggestion) {
		suggestionService.createOrUpdateSuggestion(suggestion);
		return "redirect:/admin/suggestion/index";
	}

}
