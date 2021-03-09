package com.guolong.web.admin;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.guolong.po.User;
import com.guolong.service.UserService;

@Controller
@RequestMapping("/admin")
public class LoginController {

	@Autowired
	private UserService userService;
	
	@GetMapping
	public String loginPage() {
		return "admin/login";
	}
	
	@GetMapping("/index")
	public String indexPage() {
		return "admin/index";
	}
	
	@RequestMapping("/login")
	public String login(@RequestParam(required = false)  String username,
					    @RequestParam(required = false,defaultValue="111")  String password,
					    HttpSession session,
					    RedirectAttributes attributes) {
		User user = userService.checkUser(username, password);
		if(user != null) {
			user.setPassword(null);
			session.setAttribute("user", user);
			return "admin/index";
		}else {
			attributes.addFlashAttribute("message", "用戶名和密碼錯誤");//用這個是因為返回是重定向，用這個才拿得到錯誤題示，用Model不行
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/admin";
	}
}
