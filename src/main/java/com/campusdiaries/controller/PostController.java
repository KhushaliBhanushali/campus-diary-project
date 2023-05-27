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

import com.campusdiaries.entity.Post;
import com.campusdiaries.entity.User;
import com.campusdiaries.service.PostService;
import com.campusdiaries.service.UserService;
import com.campusdiaries.util.FileUploadUtil;

@Controller
@RequestMapping(value = "admin/post")
public class PostController {
	private PostService postService;
	private UserService userService;

	public PostController(PostService postService, UserService userService) {
		this.postService = postService;
		this.userService = userService;
	}

	@GetMapping(value = "/index")
	public String posts(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
		List<Post> posts = postService.getAllPost();
		model.addAttribute("listPosts", posts);
		model.addAttribute("keyword", keyword);
		return "admin/list/posts_list";
	}

	@GetMapping(value = "/create")
	public String formPosts(Model model) {
		model.addAttribute("post", new Post());
		List<User> users = userService.getAllUser();
		model.addAttribute("listUsers", users);

		return "admin/entry/post_entry";
	}

	@GetMapping(value = "/delete/{id}")
	public String deletePost(@PathVariable(value = "id") Integer id, String keyword) {
		postService.removePost(id);
		return "redirect:/admin/post/index?keyword=" + keyword;
	}

	@GetMapping(value = "/update/{id}")
	public String updatePost(@PathVariable(value = "id") Integer id, Model model) {
		Post post = postService.loadPostById(id);
		model.addAttribute("post", post);
		List<User> users = userService.getAllUser();
		model.addAttribute("listUsers", users);

		return "admin/edit/post_edit";
	}

	@PostMapping(value = "/save")
	public String save(Post post,@RequestParam("file") MultipartFile file) throws IOException  {
	    
	    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	    if(fileName.length()>3) {
	    post.setPhoto(fileName);
	    postService.createOrUpdatePost(post);
	    String uploadDir = "assets1/images/posts";
	    FileUploadUtil.saveFile(uploadDir, fileName,file);
	    }else {
		postService.createOrUpdatePost(post);
	    }
		return "redirect:/admin/post/index";
	}

}
