package br.com.devsystem.auth.registration.token;

import java.util.Date;
import java.util.Objects;

import br.com.devsystem.auth.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

/**
 *  @author Marcelino Feliciano Sousa
 *  @version 0.0.1
 *  
 */
@Entity
public class VerificationToken {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
	private String token;
	private Date expirationTime;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VerificationToken other = (VerificationToken) obj;
		return Objects.equals(id, other.id);
	}

	public VerificationToken() {
	}

	public VerificationToken(String token, User user) {
		this.token = token;
		this.user = user;
		this.expirationTime = TokenExpirationTime.getExpirationTime();
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "VerificationToken [id=" + id + ", token=" + token + ", expirationTime=" + expirationTime + ", user="
				+ user.getEmail() + "]";
	}
	
	
}
