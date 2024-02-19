package br.com.devsystem.auth.registration;

import java.util.List;

import br.com.devsystem.auth.user.Role;

/** 
 *  Author: Marcelino Feliciano de Sousa
 *  
 */
public class RegistrationRequest {

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private List<Role> roles;
	
	private RegistrationRequest(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.password = builder.password;
        this.roles = builder.roles;
    }
	
	public static Builder builder() {
        return new Builder();
    }
	
	public static class Builder {
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private List<Role> roles;

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }
        
        public Builder roles(List<Role> roles) {
            this.roles = roles;
            return this;
        }
        
        public RegistrationRequest build() {
            return new RegistrationRequest(this);
        }
    }
		
	public RegistrationRequest() {	
	}
	
	public RegistrationRequest(String firstName, String lastName, String email, String password,
			List<Role> roles) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "RegistrationRequest [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", roles=" + roles + "]";
	}
	
}
