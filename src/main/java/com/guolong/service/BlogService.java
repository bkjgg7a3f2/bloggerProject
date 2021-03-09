package com.guolong.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.guolong.po.Blog;
import com.guolong.vo.BlogQuery;


public interface BlogService {

	public Blog getBlog(Long id);
	
	public Page<Blog> listBlog(Pageable pageable,BlogQuery blog);
	
	public Page<Blog> listBlog(Long tagId,Pageable pageable);
	
	public Blog saveBlog(Blog blog);
	
	public Blog updateBlog(Long id,Blog blog);
	
	public void deleteBlog(Long id);
	
	public Page<Blog> listBlog(Pageable pageable);
	
	public Page<Blog> listBlog(String query,Pageable pageable);
	
	public List<Blog> listRecommendBlogTop(Integer size);
	
	public Blog getAndConvert(Long id);
	
	Map<String,List<Blog>> archiveBlog();

    Long countBlog();
}
