package br.com.devsystem.auth.event;

import org.springframework.context.ApplicationEvent;

import br.com.devsystem.auth.user.User;


public class RegistrationCompleteEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	private User user;
	private String confirmationUrl;
	
	public RegistrationCompleteEvent(Object source) {
		super(source);
	}

	public RegistrationCompleteEvent(User user, String confirmationUrl) {
		super(user);
		this.user = user;
		this.confirmationUrl = confirmationUrl;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getConfirmationUrl() {
		return confirmationUrl;
	}

	public void setConfirmationUrl(String confirmationUrl) {
		this.confirmationUrl = confirmationUrl;
	}
}
