package br.com.devsystem.auth.registration.token;

import java.util.Optional;

import br.com.devsystem.auth.user.User;

/**
 *  @author Marcelino Feliciano Sousa
 *  
 */
public interface IVerificationTokenService {

	String validateToken(String token);
	
	void saveVerificationTokenForUser(User user, String token );
	
	Optional<VerificationToken> findByToken(String token);
	
	
}
