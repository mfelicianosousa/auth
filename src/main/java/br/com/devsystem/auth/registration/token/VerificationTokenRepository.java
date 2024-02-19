package br.com.devsystem.auth.registration.token;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  @author Marcelino Feliciano Sousa
 *  @version 0.0.1
 *  
 */
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

	Optional<VerificationToken> findByToken(String token);

	void deleteByUserId(Long id);
	
}
