package com.blogbackend.blogbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogbackend.blogbackend.model.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {// *1 class mapping database 2 primary key

	
}
