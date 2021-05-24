package com.blogbackend.blogbackend.controller.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.blogbackend.blogbackend.controller.request.BlogRequest;
import com.blogbackend.blogbackend.model.Blog;
import com.blogbackend.blogbackend.service.BlogService;

@RestController
@RequestMapping("/blog")
@CrossOrigin("http://localhost:4200")
public class BlogController {
	private final BlogService blogService;

	public BlogController(BlogService blogService) {
		this.blogService = blogService;

	}

	// * Get------
	@GetMapping()
	public List<Blog> getBlog() {
		return blogService.getAllBlog();

	}

	// * GetById-----
	@GetMapping("/getById/{id}")
	public Blog getBlogById(@PathVariable long id) {
		return blogService.getBlogById(id);
	}

	// *Post-------------
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/create")
	public Blog createBlog(@RequestBody BlogRequest blogRequest) {
		return blogService.createBlog(blogRequest);
	}

	// *PUT-------------------
	@PutMapping("/update/{id}")
	public Blog updateBlog(@RequestBody BlogRequest blogRequest, @PathVariable long id) {
		return blogService.updateBlog(blogRequest, id);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/delete/{id}")
	public void deleteBlog(@PathVariable long id) {
		blogService.deleteBlog(id);
	}

}
