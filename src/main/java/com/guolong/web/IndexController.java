package com.guolong.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.guolong.NotFoundException;
import com.guolong.po.Blog;
import com.guolong.service.BlogService;
import com.guolong.service.TagService;
import com.guolong.service.TypeService;

@Controller
public class IndexController {
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private TypeService typeService;
	
	@GetMapping("/")
	public String index(@PageableDefault(size=5,sort = {"updateTime"},direction = Direction.ASC) Pageable pageable,
						Model model) {
		
		
		
		model.addAttribute("page", blogService.listBlog(pageable));
		model.addAttribute("types", typeService.listTypeTop(6));
		model.addAttribute("tags", tagService.listTagTop(10));
		model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
		return "index";
	}
	
	@PostMapping("/search")
	public String search(@PageableDefault(size=10,sort = {"updateTime"},direction = Direction.ASC) Pageable pageable,
			Model model,@RequestParam String query) {
		
		model.addAttribute("page", blogService.listBlog("%"+query+"%", pageable));
		model.addAttribute("query", query);
		return "search";
	}
	
	@GetMapping("/blog/{id}")
	public String blog(@PathVariable Long id,Model model) {
		model.addAttribute("blog", blogService.getAndConvert(id));
		return "blog";
	}
	
	@GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("newblogs", blogService.listRecommendBlogTop(3));
        return "index :: newblogList";
    }
}
