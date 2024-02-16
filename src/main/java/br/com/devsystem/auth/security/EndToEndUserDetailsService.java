package br.com.devsystem.auth.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.devsystem.auth.user.UserRepository;

@Service
public class EndToEndUserDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;

	public EndToEndUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	
		return userRepository.findByEmail(email)
				.map(EndToEndUserDetails::new) 
				.orElseThrow(()-> new UsernameNotFoundException("User not found"));
	}
	
	
	

}
