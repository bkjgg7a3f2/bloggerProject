package com.guolong.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.guolong.po.Comment;
import com.guolong.po.User;
import com.guolong.service.BlogService;
import com.guolong.service.CommentService;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private BlogService blogService; 
	
	@GetMapping("/comments/{blogId}")
	public String comments(@PathVariable Long blogId,Model model) {
		model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
		return "blog :: commentList";
	}
	
	@PostMapping("/comments")
	public String post(Comment comment,HttpSession session) {
		User user = (User) session.getAttribute("user");
		Long blogid = comment.getBlog().getId();
		comment.setBlog(blogService.getBlog(blogid));
		if(user != null) {
			comment.setAvatar(user.getAvatar());
			comment.setAdminComment(true);
		}else {
			comment.setAvatar("/images/idiot.jpg");
		}
		commentService.saveComment(comment);
		return "redirect:/comments/"+ blogid;
	}
}
