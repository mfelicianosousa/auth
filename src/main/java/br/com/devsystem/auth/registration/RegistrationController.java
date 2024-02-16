package br.com.devsystem.auth.registration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.devsystem.auth.user.IUserService;
import br.com.devsystem.auth.user.User;
import br.com.devsystem.auth.user.registration.event.RegitrationCompleteEvent;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

	private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);
	private final IUserService userService;
	private final ApplicationEventPublisher publisher;

	public RegistrationController(IUserService userService,
			ApplicationEventPublisher publisher) {
		this.userService = userService;
		this.publisher= publisher;

	}
	
	@GetMapping("/registration-form")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new RegistrationRequest());
		return "registration";
	}
	
	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") RegistrationRequest registration ) {
		log.info("Registration ..."+registration.getFirstName()+
				  ','+registration.getLastName()+','+registration.getEmail());
		
		User user = userService.registerUser(registration);
		log.info("User ..."+user.toString());
		
		// publish the verification email event here
		//publisher.publishEvent(new RegitrationCompleteEvent(user, ""));
		return "redirect:/registration/registration-form?success";
	}
}
