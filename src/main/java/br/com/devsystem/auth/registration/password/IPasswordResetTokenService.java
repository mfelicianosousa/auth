package br.com.devsystem.auth.registration.password;

import java.util.Optional;

import br.com.devsystem.auth.user.User;

public interface IPasswordResetTokenService {

	void createPasswordResetTokenForUser(User user, String passwordResetToken);

	String validatePasswordResetToken(String theToken);

	Optional<User> findUserByPasswordResetToken(String theToken);

	void resetPassword(User theUser, String newPassword);
	
}
