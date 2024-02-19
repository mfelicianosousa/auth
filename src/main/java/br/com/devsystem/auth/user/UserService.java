package br.com.devsystem.auth.user;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.devsystem.auth.registration.RegistrationRequest;
import br.com.devsystem.auth.registration.token.VerificationTokenService;
import jakarta.transaction.Transactional;

/** 
 *  Author: Marcelino Feliciano de Sousa
 *  
 */
@Service
public class UserService implements IUserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final VerificationTokenService verificationTokenService;
	
	public UserService( UserRepository userRepository,
			PasswordEncoder passwordEncoder,
			VerificationTokenService verificationTokenService ) {
	
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.verificationTokenService = verificationTokenService;
	}

	@Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(RegistrationRequest registration) {
        var user = new User(
        		registration.getFirstName(), 
        		registration.getLastName(),
                registration.getEmail(),
                passwordEncoder.encode(registration.getPassword()),
                Arrays.asList(new Role("ROLE_USER"))
                );
       return userRepository.save( user );
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    @Override
    public void updateUser(Long id, String firstName, String lastName, String email) {
        userRepository.update(firstName, lastName, email, id);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        Optional<User> theUser = userRepository.findById(id);
        theUser.ifPresent(user -> verificationTokenService.deleteUserToken(user.getId()));
        userRepository.deleteById(id);
    }
}
