package br.com.devsystem.auth.registration;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.devsystem.auth.event.RegistrationCompleteEvent;
import br.com.devsystem.auth.registration.token.VerificationToken;
import br.com.devsystem.auth.registration.token.VerificationTokenService;
import br.com.devsystem.auth.user.IUserService;
import br.com.devsystem.auth.user.User;
import br.com.devsystem.auth.utility.UrlUtil;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

	private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);
	private final IUserService userService;
	private final ApplicationEventPublisher publisher;
	private final VerificationTokenService tokenService;

	public RegistrationController(IUserService userService,
			ApplicationEventPublisher publisher,
			VerificationTokenService tokenService) {
		this.userService = userService;
		this.publisher= publisher;
		this.tokenService = tokenService;
	}
	
	@GetMapping("/registration-form")
	public String showRegistrationForm(Model model) {
		log.info("Registration-form");
		model.addAttribute("user", new RegistrationRequest());
		return "registration";
	}
	
	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") RegistrationRequest registration, HttpServletRequest request ) {	
		User user = userService.registerUser(registration);
		
		// publish the verification email event here
		publisher.publishEvent(new RegistrationCompleteEvent(user, UrlUtil.getApplicationUrl(request)));
		return "redirect:/registration/registration-form?success";
	}
	
	@GetMapping("/verifyEmail")
	public String verifyEmail(@RequestParam("token") String token) {
		log.info("->VerifyEmail:");
		Optional<VerificationToken> theToken = tokenService.findByToken(token);
		/*
	    if (theToken.isPresent() && theToken.get().getUser().isEnabled()) {
	    	log.info("IsPresent ..."+theToken.isPresent());
	    	log.info("isEnabled ..."+theToken.get().getUser().isEnabled());
	    	
	    	return "redirect:/login?verified";
        }
        */
        String verificationResult = tokenService.validateToken(token);
        
        log.info("VerificationResult ..."+verificationResult.toString());
        
        switch (verificationResult.toLowerCase()) {
            case "expired":
                return "redirect:/error?expired";
            case "valid":
                return "redirect:/login?valid";
            default:
                return "redirect:/error?invalid";
        }
	}
}
