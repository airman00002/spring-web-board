package com.blogbackend.blogbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogbackend.blogbackend.controller.request.BlogRequest;
import com.blogbackend.blogbackend.exception.BlogNotFoundException;
import com.blogbackend.blogbackend.model.Blog;
import com.blogbackend.blogbackend.repository.BlogRepository;

@Service
public class BlogServiceImp implements BlogService {

	private final BlogRepository blogRepository;

	public BlogServiceImp(BlogRepository blogRepository) {
		this.blogRepository = blogRepository;

	}

	// *-----Get-----
	@Override
	public List<Blog> getAllBlog() {
		return blogRepository.findAll(Sort.by(Sort.Direction.ASC,"id"));
//		return productRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate")); // * select *
	}

	// *-----Get---By---ID--------
	@Override
	public Blog getBlogById(Long id) {
		Optional<Blog> blog = blogRepository.findById(id);
		if (blog.isPresent()) {
			return blog.get();
		}
		throw new BlogNotFoundException(id);
	}

	// *-----Post---------
	@Override
	public Blog createBlog(BlogRequest blogRequest) {
		Blog data = new Blog();
		data.setName(blogRequest.getName()).setImage(blogRequest.getImage())
				.setDescription(blogRequest.getDescription());

		return blogRepository.save(data);

	}

	// *-----PUT----------
	@Override
	public Blog updateBlog(BlogRequest blogRequest, Long id) {
		Optional<Blog> blog = blogRepository.findById(id);
		if (blog.isPresent()) {
			Blog editBlog = blog.get();

			editBlog.setName(blogRequest.getName()).setImage(blogRequest.getImage())
					.setDescription(blogRequest.getDescription());
			return blogRepository.save(editBlog);
		}
		throw new BlogNotFoundException(id);
	}

	// *-----DELETE----------
	@Override
	public void deleteBlog(Long id) {
		try {
			blogRepository.deleteById(id);
		} catch (Exception e) {
			throw new BlogNotFoundException(id);
		}

	}

}
