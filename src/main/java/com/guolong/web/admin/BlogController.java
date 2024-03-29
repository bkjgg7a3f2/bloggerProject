package com.guolong.web.admin;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.guolong.po.Blog;
import com.guolong.po.User;
import com.guolong.service.BlogService;
import com.guolong.service.TagService;
import com.guolong.service.TypeService;
import com.guolong.vo.BlogQuery;

@Controller
@RequestMapping("/admin")
public class BlogController {
	
	private static final String INPUT = "admin/blogs-input";
	private static final String LIST = "admin/blogs";
	private static final String REDIRECT_LIST = "redirect:/admin/blogs";

	@Autowired
	private BlogService blogService;
	
	@Autowired
	private TypeService typeService;
	
	@Autowired
	private TagService tagService;
	
	@GetMapping("/blogs")
	public String blogs(Model model,@PageableDefault(size=5,sort = {"updateTime"},direction = Direction.ASC) 
						Pageable pageable,
						BlogQuery bq) {
		model.addAttribute("types", typeService.listType());
		model.addAttribute("page",blogService.listBlog(pageable, bq));
		return LIST;
	}
	
	@PostMapping("/blogs/search")
	public String search(Model model,@PageableDefault(size=5,sort = {"updateTime"},direction = Direction.ASC) 
						Pageable pageable,
						BlogQuery bq) {
		model.addAttribute("page",blogService.listBlog(pageable, bq));
		return "admin/blogs :: blogList";
	}
	
	@GetMapping("blogs/input")
	public String input(Model model) {
		model.addAttribute("types", typeService.listType());
		model.addAttribute("tags", tagService.listTag());
		model.addAttribute("blog", new Blog());
		return INPUT;
	}
	
	@GetMapping("blogs/{id}/input")
	public String editInput(@PathVariable Long id,Model model) {
		model.addAttribute("types", typeService.listType());
		model.addAttribute("tags", tagService.listTag());
		Blog blog = blogService.getBlog(id);
		blog.init();
		model.addAttribute("blog", blog);
		return INPUT;
	}
	
	@PostMapping("/blogs")
	public String post(Blog blog,HttpSession session,RedirectAttributes attributes) {
		blog.setUser((User)session.getAttribute("user"));
		blog.setType(typeService.getType(blog.getType().getId()));
		blog.setTags(tagService.listTag(blog.getTagIds()));
		Blog b = blogService.saveBlog(blog);
		if(b == null) {
			attributes.addFlashAttribute("message", "操作失敗");
		}else {
			attributes.addFlashAttribute("message", "操作成功");
		}
		return REDIRECT_LIST;
	}
	
	@GetMapping("/blogs/{id}/delete")
	public String delete(@PathVariable Long id,RedirectAttributes attributes) {
		blogService.deleteBlog(id);
		attributes.addFlashAttribute("message", "刪除成功");
		return REDIRECT_LIST;
	}
	
}
