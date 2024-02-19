package br.com.devsystem.auth.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.devsystem.auth.registration.token.VerificationTokenService;
import br.com.devsystem.auth.user.UserRepository;

@Service
public class EndToEndUserDetailsService implements UserDetailsService {
	
	private static final Logger log = LoggerFactory.getLogger(EndToEndUserDetailsService.class);
	
	private final UserRepository userRepository;

	public EndToEndUserDetailsService(UserRepository userRepository) {
	
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		log.info("EndToEndUserDetailsService.class -> LoadUserByUsername() email "+email);
		
		return userRepository.findByEmail(email)
				.map(EndToEndUserDetails::new) 
				.orElseThrow(()-> new UsernameNotFoundException("User not found"));
	}
}
