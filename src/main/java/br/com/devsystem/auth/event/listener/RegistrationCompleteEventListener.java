package br.com.devsystem.auth.event.listener;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import br.com.devsystem.auth.event.RegistrationCompleteEvent;
import br.com.devsystem.auth.registration.RegistrationController;
import br.com.devsystem.auth.registration.token.VerificationTokenService;
import br.com.devsystem.auth.user.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

	private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);
	private final VerificationTokenService tokenService;
	private final JavaMailSender mailSender;
	private User user;
	
	public RegistrationCompleteEventListener(VerificationTokenService tokenService,
			    JavaMailSender mailSender) {
		this.tokenService = tokenService;
		this.mailSender = mailSender;
		log.info("<RegistrationCompleteEventListener.class>");
	}

	@Override
	public void onApplicationEvent(RegistrationCompleteEvent event) {
		log.info("... onApplicationEvent(): ");
		// 1. get the user
		user = event.getUser();
		
		// 2. generate a token for the user
		String vToken = UUID.randomUUID().toString();
		// 3. save the token for the user
		tokenService.saveVerificationTokenForUser(user, vToken);
		// 4. build the verification url
		String url = event.getConfirmationUrl() + "/registration/verifyEmail?token=" + vToken;
		// 5.send the email to the user
		try {
			log.info(" ... user : "+user.toString());
			log.info(" ... url "+url);
			sendVerificationEmail( url );
			
		} catch (UnsupportedEncodingException | MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {

		String subject = "Email Verification";
		String senderName = "Users Verification Service";
		String mailContent = "<p> Hi, " + user.getFirstName() + ", </p>" + "<p> Thank you for registering with us," + ""
				+ "Please, follow the link below to complete your registration.<p>" + "<a href=\"" + url
				+ "\">Verify your email to activate your account</a>"
				+ "<p> Thank you <br> Users Registration Portal Service";
		emailMessage(subject, senderName, mailContent, mailSender, user);
	}
	public void sendPasswordResetVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Password Reset Request Verification";
        String senderName = "Users Verification Service";
        String mailContent = "<p> Hi, "+ user.getFirstName() + ", </p>"+
                "<p><b>You recently requested to reset your password. </b>"+"" +
                "Please, follow the link below to complete the action.</p>"+
                "<a href=\"" +url+ "\">Reset password</a>"+
                "<p> Users Registration Portal Service";
        emailMessage(subject, senderName, mailContent, mailSender, user);
    }

	private static void emailMessage(String subject, String senderName, String mailContent, JavaMailSender mailSender,
			User theUser)  {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			var messageHelper = new MimeMessageHelper(message);
			messageHelper.setFrom("marcelino.feliciano@outlook.com", senderName);
			messageHelper.setTo(theUser.getEmail());
			messageHelper.setSubject(subject);
			messageHelper.setText(mailContent, true);
			mailSender.send(message);
		} catch (MessagingException| UnsupportedEncodingException e) { 
				throw new RuntimeException(e); 
		}
	}

}
