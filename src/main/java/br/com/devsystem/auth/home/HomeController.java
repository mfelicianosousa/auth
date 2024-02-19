package br.com.devsystem.auth.home;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class HomeController {
	 
	@GetMapping
	public String homePage() {
		return "home";
	}
    
    @GetMapping("/login")
   	public String login() {
   		return "login";
   	}
    
    @GetMapping("/error")
   	public String error() {
   		return "error";
   	}
    
}
