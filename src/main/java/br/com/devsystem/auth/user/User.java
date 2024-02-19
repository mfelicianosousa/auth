package br.com.devsystem.auth.user;

import java.util.Collection;
import java.util.Objects;

import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/** 
 *  Author: Marcelino Feliciano de Sousa
 *  
 */
@Entity
@Table(name="users")
public class User {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;
	
	private String firstName;
	private String lastName;
	
	@NaturalId(mutable = true)
	private String email;
	private String password;
	private boolean isEnabled;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name="user_roles", 
	     joinColumns = @JoinColumn( name="user_id", 
	     referencedColumnName="id"), 
	     inverseJoinColumns = @JoinColumn( name = "role_id",
	     referencedColumnName="id"))
	private Collection<Role> roles;
	
	private User(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.password = builder.password;
        this.isEnabled = builder.isEnabled;
        this.roles = builder.roles;

    }
	
	public static Builder builder() {
        return new Builder();
    }
	
	public static class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private boolean isEnabled;
        private Collection<Role> roles;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

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
        
        public Builder isEnabled(boolean isEnabled) {
            this.isEnabled = isEnabled;
            return this;
        }
        public Builder roles(Collection<Role> roles) {
            this.roles = roles;
            return this;
        }
        
        public User build() {
            return new User(this);
        }
    }
	
	
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
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}

	public User() {
		
	}
	public User(Long id, String firstName, String lastName, String email, 
			String password, boolean isEnabled,Collection<Role> roles) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.isEnabled = isEnabled;
		this.roles = roles;
	}
	
	public User(String firstName, String lastName, String email, 
			String password, Collection<Role> roles) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}
	
	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastname=" + lastName + ", email=" + email
				+ ", password=" + password + ", isEnabled=" + isEnabled + ", roles=" + roles + "]";
	}

	public String getPassword() {
		
		return password;
	}

	public boolean isEnabled() {

		return isEnabled;
	}

	
}
