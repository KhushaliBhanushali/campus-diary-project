package com.campusdiaries.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.campusdiaries.entity.CommentPost;
import com.campusdiaries.entity.Post;
import com.campusdiaries.entity.User;
import com.campusdiaries.service.CommentPostService;
import com.campusdiaries.service.PostService;
import com.campusdiaries.service.UserService;

@Controller
@RequestMapping(value = "admin/commentPost")
public class CommentPostController {
	private CommentPostService commentPostService;
	private PostService postService;
	private UserService userService;

	public CommentPostController(CommentPostService commentPostService, PostService postService,
			UserService userService) {
		this.commentPostService = commentPostService;
		this.postService = postService;
		this.userService = userService;
	}

	@GetMapping(value = "/index")
	public String commentPosts(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
		List<CommentPost> commentPosts = commentPostService.getAllCommentPost();
		model.addAttribute("listCommentPosts", commentPosts);
		model.addAttribute("keyword", keyword);
		return "admin/list/commentPosts_list";
	}

	@GetMapping(value = "/create")
	public String formCommentPosts(Model model) {
		model.addAttribute("commentPost", new CommentPost());
		List<Post> posts = postService.getAllPost();
		model.addAttribute("listPosts", posts);

		List<User> users = userService.getAllUser();
		model.addAttribute("listUsers", users);

		return "admin/entry/commentPost_entry";
	}

	@GetMapping(value = "/delete/{id}")
	public String deleteCommentPost(@PathVariable(value = "id") Integer id, String keyword) {
		commentPostService.removeCommentPost(id);
		return "redirect:/admin/commentPost/index?keyword=" + keyword;
	}

	@GetMapping(value = "/update/{id}")
	public String updateCommentPost(@PathVariable(value = "id") Integer id, Model model) {
		CommentPost commentPost = commentPostService.loadCommentPostById(id);
		model.addAttribute("commentPost", commentPost);
		List<Post> posts = postService.getAllPost();
		model.addAttribute("listPosts", posts);

		List<User> users = userService.getAllUser();
		model.addAttribute("listUsers", users);

		return "admin/edit/commentPost_edit";
	}

	@PostMapping(value = "/save")
	public String save(CommentPost commentPost) {
		commentPostService.createOrUpdateCommentPost(commentPost);
		return "redirect:/admin/commentPost/index";
	}

}
