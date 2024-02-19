package br.com.devsystem.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
	            .requestMatchers("/", "/registration/**").permitAll()
	            .requestMatchers("/login","/error","/users").permitAll()
	            .requestMatchers("assets/css","assets/js","assets/images").permitAll()
	         //   .requestMatchers("/registration/**").permitAll()
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
	            .logout((logout) -> logout
	 	             .deleteCookies("custom-cookie") //<
	            	 .clearAuthentication(true) 
	            	 .invalidateHttpSession(true) 
	                 .logoutUrl("/logout") 
	                 .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	                 .logoutSuccessUrl("/")  
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
				     //.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
				   .invalidateHttpSession(true)
				   .clearAuthentication(true)
				   .logoutRequestMatcher("/logout")
				   .logoutSuccessUrl("/")
				   .and()
				   .build();
				 */  
	}
}
