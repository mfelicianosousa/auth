package br.com.devsystem.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class EndToEndSecurityDemo {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	} 
	
	@Bean
	public SecurityFilterChain securityFilterChain( HttpSecurity http) throws Exception {
		return http.authorizeHttpRequests(
	            auth -> 
	            auth
	            .requestMatchers("/").permitAll()
	            .requestMatchers("/login", "/logout").permitAll()
	            .requestMatchers("/registration/**").permitAll()
	        //    .requestMatchers("/myapps/**").hasAuthority("CLIENT")
	            .anyRequest().authenticated()
	           )
	            .formLogin(formLogin -> formLogin
	                    .loginPage("/login")
	                    .usernameParameter("email")
	                    .defaultSuccessUrl("/", true)
	                    .permitAll()
	                    
	            )
	           // .rememberMe(rememberMe -> rememberMe.key("AbcdEfghIjkl..."))
	            .logout(logout -> logout 
	                 .logoutUrl("/logout") 
	                 .logoutSuccessUrl("/")
	                 .deleteCookies("custom-cookie")
	                 .permitAll()
	             )
	            .build();
	 
		
				/*http.csrf()
				   .disable()
				   .authorizeHttpRequests()
				   .requestMatchers("/","/registration/**")
				   .permitAll().anyRequest()
				   .authenticated()
				   .and()
				   .formLogin()
				   .loginPage("/login")
				   .usernameParameter("email")
				   .defaultSuccessUrl("/")
				   .permitAll().and().logout()
				   .invalidateHttpSession(true)
				   .clearAuthentication(true)
				   .logoutRequestMatcher("/logout")
				   .logoutSuccessUrl("/")
				   .and()
				   .build();
				 */  
	}
}
