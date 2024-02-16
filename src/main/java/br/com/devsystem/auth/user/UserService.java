package br.com.devsystem.auth.user;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.devsystem.auth.registration.RegistrationRequest;

@Service
public class UserService implements IUserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository,
			PasswordEncoder passwordEncoder) {
	
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();		
	}

	@Override
	public User registerUser(RegistrationRequest registration) {
		var user = new User(registration.getFirstName(),
			registration.getLastName(),
			registration.getEmail(),
			passwordEncoder.encode(registration.getPassword()),
			Arrays.asList( new Role("ROLE_USER")));
		
		return userRepository.save(user);
		
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(()-> new UsernameNotFoundException("User Not found"));
	}
}
