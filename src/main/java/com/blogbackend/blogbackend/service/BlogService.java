package com.blogbackend.blogbackend.service;

import java.util.List;

import com.blogbackend.blogbackend.controller.request.BlogRequest;
import com.blogbackend.blogbackend.model.Blog;

public interface BlogService {
	
	List<Blog> getAllBlog();
	
	Blog getBlogById(Long id);
	
	Blog createBlog(BlogRequest blogRequest );
	
	Blog updateBlog(BlogRequest blogRequest,Long id);
	
	void deleteBlog(Long id);
	
	
	
	
}
