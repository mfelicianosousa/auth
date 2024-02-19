package br.com.devsystem.auth.user;

import java.util.List;
import java.util.Optional;

import br.com.devsystem.auth.registration.RegistrationRequest;

public interface IUserService {
	
	 List<User> getAllUsers();
	 
	 User registerUser( RegistrationRequest registrationRequest );
	 
	 Optional<User> findByEmail( String email );

	 Optional<User> findById( Long id );

	 void updateUser( Long id, String firstName, String lastName, String email);

	 void deleteUser( Long id);
	
	

}
