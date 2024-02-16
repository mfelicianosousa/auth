package br.com.devsystem.auth.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.devsystem.auth.user.User;

public class EndToEndUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String password;
	private boolean isEnabled;
	private List<GrantedAuthority> authorities;
	
	public EndToEndUserDetails(User user) {
		this.userName = user.getEmail();
		this.password = user.getPassword();
		this.isEnabled = user.isEnabled() ; 
	    this.authorities = Arrays.stream(user.getRoles().toString().split(","))
                .map(SimpleGrantedAuthority::new) 
                .collect(Collectors.toList());
	    
		/* 
		 *     this.authorities = 
				Arrays.stream( user.getRoles().toString().split(","))
				.map( SimpleGrantedAuthority::new) Stream<SimpleGrantedAuthoritory>
		        .collect(Collectors.toList())
		   */
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return authorities;
	}

	@Override
	public String getPassword() {

		return password;
	}

	@Override
	public String getUsername() {
		
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
	
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
	
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {
	
		return isEnabled();
	}	

}
