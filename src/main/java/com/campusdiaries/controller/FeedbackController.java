package com.campusdiaries.controller;

import com.campusdiaries.entity.Feedback; 
import com.campusdiaries.service.FeedbackService; 
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestParam; 
import java.util.List; 
@Controller 
@RequestMapping(value = "admin/feedback") 
public class FeedbackController { 
 private FeedbackService feedbackService; 
    public FeedbackController(FeedbackService feedbackService) { 
        this.feedbackService = feedbackService; 
    }
 
    @GetMapping(value = "/index") 
    public String feedbacks(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) { 
        List<Feedback> feedbacks = feedbackService.getAllFeedback(); 
        model.addAttribute("listFeedbacks", feedbacks); 
        model.addAttribute("keyword", keyword); 
        return "admin/list/feedbacks_list"; 
    }
 
  @GetMapping(value = "/create") 
    public String formFeedbacks(Model model) { 
        model.addAttribute("feedback", new Feedback()); 
        return "admin/entry/feedback_entry"; 
    } 
    @GetMapping(value = "/delete/{id}") 
    public String deleteFeedback(@PathVariable(value = "id") Integer id, String keyword) { 
        feedbackService.removeFeedback(id); 
        return "redirect:/admin/feedback/index?keyword=" + keyword; 
    }
 
    @GetMapping(value = "/update/{id}") 
    public String updateFeedback(@PathVariable(value = "id") Integer id, Model model) { 
        Feedback feedback = feedbackService.loadFeedbackById(id); 
        model.addAttribute("feedback", feedback); 
        return "admin/edit/feedback_edit"; 
    }
 
    @PostMapping(value = "/save") 
    public String save(Feedback feedback) { 
        feedbackService.createOrUpdateFeedback(feedback); 
        return "redirect:/admin/feedback/index"; 
    }
 
} 
