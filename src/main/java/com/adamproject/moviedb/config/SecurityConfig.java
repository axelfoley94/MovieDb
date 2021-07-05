package com.adamproject.moviedb.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.adamproject.moviedb.service.MyUserDetailsService;


//import com.adamproject.moviedb.service.MyUserServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private final MyUserDetailsService myUserDetailsService;
	
	@Autowired
	public SecurityConfig(MyUserDetailsService myUserDetailsService) {
		this.myUserDetailsService = myUserDetailsService;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}


	private static final String[] PUBLIKUS_NEZETEK = {
			"/css/**",
			"/js/**",
			"/imgs/**",
			"/icons/**",
			"/webfonts/**",
			"/files/**",
			"/home",
			"/",
			"/register",
			"/favicon.ico",
			"favicon",
			"favicon.ico",
			"/brand-logos/**"
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers(PUBLIKUS_NEZETEK).permitAll()
			.antMatchers("/actorList").hasRole("ADMIN")
			.anyRequest().authenticated()
			.and()
			.formLogin().failureUrl("/login?error").defaultSuccessUrl("/", true).loginPage("/login").permitAll()
//			.and()
//			.rememberMe()
//				.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
//				.key("verysecuredtext")
//				//.rememberMeParameter("remember-me")
			.and()
			.logout().logoutUrl("/logout")
			.clearAuthentication(true)
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.logoutSuccessUrl("/")
		;}


	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
		return daoAuthenticationProvider;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		//return NoOpPasswordEncoder.getInstance();
	}
	
}
