package br.com.devsystem.auth.registration;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

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
import br.com.devsystem.auth.event.listener.RegistrationCompleteEventListener;
import br.com.devsystem.auth.registration.password.IPasswordResetTokenService;
import br.com.devsystem.auth.registration.token.VerificationToken;
import br.com.devsystem.auth.registration.token.VerificationTokenService;
import br.com.devsystem.auth.user.IUserService;
import br.com.devsystem.auth.user.User;
import br.com.devsystem.auth.utility.UrlUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

	private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);
	private final IUserService userService;
	private final ApplicationEventPublisher publisher;
	private final VerificationTokenService tokenService;
	private final IPasswordResetTokenService passwordResetTokenService;
	private final RegistrationCompleteEventListener eventListener;
	
	public RegistrationController(IUserService userService,
			ApplicationEventPublisher publisher,
			VerificationTokenService tokenService,
			IPasswordResetTokenService passwordResetTokenService,
			RegistrationCompleteEventListener eventListener) {
		this.userService = userService;
		this.publisher= publisher;
		this.tokenService = tokenService;
		this.passwordResetTokenService = passwordResetTokenService;
		this.eventListener = eventListener;
		log.info("<RegistrationController.class>");
	}
	
	@GetMapping("/registration-form")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new RegistrationRequest());
		return "registration";
	}
	
	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") RegistrationRequest registration, HttpServletRequest request ) {	
		User user = userService.registerUser(registration);
		log.info(".../register");
		// publish the verification email event here
		publisher.publishEvent(new RegistrationCompleteEvent(user, UrlUtil.getApplicationUrl(request)));
		return "redirect:/registration/registration-form?success";
	}
	
	@GetMapping("/verifyEmail")
	public String verifyEmail(@RequestParam("token") String token) {
		Optional<VerificationToken> theToken = tokenService.findByToken(token);
		log.info(".../verifyEmail : "+theToken.toString());
		
	    if (theToken.isPresent() && theToken.get().getUser().isEnabled()) {
	    	log.info(" IsPresent ..."+theToken.isPresent());
	    	log.info(" isEnabled ..."+theToken.get().getUser().isEnabled());
	    	
	    	return "redirect:/login?verified";
        }
        
        String verificationResult = tokenService.validateToken(token);
        
        log.info(" var verificationResult = "+verificationResult);
        
        switch (verificationResult.toLowerCase()) {
            case "expired":
            	log.info("/verificationResult: redirect:/error?expired");
                return "redirect:/error?expired";
            case "valid":
            	log.info("/verificationResult: redirect:/login?valid");
                return "redirect:/login?valid";
            default:
            	log.info("/verificationResult: redirect:/error?invalid");
                return "redirect:/error?invalid";
        }
	}
	
	@GetMapping("/forgot-password-request")
	public String forgotPasswordForm() {
		return "forgot-password-form";
	}
	
	@PostMapping("/forgot-password")
	public String resetPasswordRequest(HttpServletRequest request, Model model) {
		log.info("/forgot-passowrd ");
		String email = request.getParameter("email");
		Optional<User> user = userService.findByEmail(email);
		if(user == null) {
			return "redirect:/registration/forgot-password-request?not-found";
		}
		String passwordResetToken = UUID.randomUUID().toString();
		passwordResetTokenService.createPasswordResetTokenForUser(user.get(), passwordResetToken);
		// send password reset verification email to the user
		String url = UrlUtil.getApplicationUrl(request)+"/registration/password-reset-form?token="+passwordResetToken;
		try {
			eventListener.sendPasswordResetVerificationEmail( url );
		} catch (MessagingException | UnsupportedEncodingException e) {
			model.addAttribute("error", e.getMessage());
		}	
		return "redirect:/registration/forgot-password-request?success";
	}
	
	@GetMapping("/password-reset-form")
	public String passwordResetForm(@RequestParam("token") String token, Model model) {
		model.addAttribute("token", token);
		return "password-reset-form";
	}
	
	@PostMapping("/reset-password")
	public String resetPassword(HttpServletRequest request) {
		String theToken = request.getParameter("token");
		String password = request.getParameter("password");
		String tokenVerificationResult = passwordResetTokenService.validatePasswordResetToken(theToken);
		// come back here
		if (!tokenVerificationResult.equalsIgnoreCase("valid")) {
			return "redirect:/error?invalid_token";
		}
		Optional<User> theUser = passwordResetTokenService.findUserByPasswordResetToken(theToken);
		if( theUser.isPresent()) {
			passwordResetTokenService.resetPassword(theUser.get(), password);
			return "redirect:/login?reset_success";
		}
		return "redirect:/error?not_found";	
	}
}
