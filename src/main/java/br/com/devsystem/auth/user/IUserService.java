package br.com.devsystem.auth.user;

import java.util.List;
import java.util.Optional;

import br.com.devsystem.auth.registration.RegistrationRequest;

public interface IUserService {
	
	List<User> getAllUsers();
	
	User registerUser(RegistrationRequest registration);
	
	User findByEmail(String email);
	
	

}
