package br.com.devsystem.auth.registration.token;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.devsystem.auth.user.User;
import br.com.devsystem.auth.user.UserRepository;
import lombok.RequiredArgsConstructor;

/**
 *  @author Marcelino Feliciano Sousa
 *  
 */
@Service
public class VerificationTokenService implements IVerificationTokenService {

	private final VerificationTokenRepository tokenRepository;
	private final UserRepository userRepository;
	

	public VerificationTokenService(VerificationTokenRepository tokenRepository, UserRepository userRepository) {
		this.tokenRepository = tokenRepository;
		this.userRepository = userRepository;
	}

	@Override
	public String validateToken(String token) {
		Optional<VerificationToken> theToken = tokenRepository.findByToken(token);
		if(theToken.isEmpty()) {
			return "INVALID";
		}
		User user = theToken.get().getUser();
		Calendar calendar = Calendar.getInstance();
		if ((theToken.get()
				   .getExpirationTime()
				   .getTime() - calendar.getTime().getTime()) <= 0) {
			return "EXPIRED";
		}
		user.setEnabled(true);
		userRepository.save(user);
		return "VALID" ;
	}

	@Override
	public void saveVerificationTokenForUser(User user, String token) {
		var verificationToken = new VerificationToken(token, user);
		tokenRepository.save(verificationToken);
	}

	@Override
	public Optional<VerificationToken> findByToken(String token) {
	
		return tokenRepository.findByToken(token);
	}

}
