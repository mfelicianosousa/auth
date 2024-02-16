package br.com.devsystem.auth.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
	
	private final IUserService userService;

	public UserController(IUserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public String getUser(Model model) {
		model.addAttribute("user", userService.getAllUsers());
		return "users";
	}
	

}
