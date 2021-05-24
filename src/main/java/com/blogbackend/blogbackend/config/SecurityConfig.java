package com.blogbackend.blogbackend.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.blogbackend.blogbackend.security.CustomUserDetailsService;
import com.blogbackend.blogbackend.security.JWTAuthenticationFilter;
import com.blogbackend.blogbackend.security.JWTAuthorizationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final CustomUserDetailsService customUserDetailsService;

	public SecurityConfig(CustomUserDetailsService customUserDetailsService,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.customUserDetailsService = customUserDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;

	}

	// *custom config ของทาง user ที่มีการเข้ารหัส
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {// *มีการเข้ารหัสนะ ฉีด
																					// //*เพื่อบอกว่าจะ custom
		auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);// bcrypt เข้าไป
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and()
				.csrf().disable().authorizeRequests() //* ปิด 
				.antMatchers("/auth/register","/blog").permitAll() // *ยอมรับแค่ ***
				.anyRequest().authenticated()
				.and().exceptionHandling().authenticationEntryPoint((req, res, error) -> {
					res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				}) // *ต่อไปจะ filter ลักษณะ JWT
				.and().addFilter(authenticationFilter()).sessionManagement()
				// *กรณีขอ resouce ต้อง addfilter
				.and().addFilter(new JWTAuthorizationFilter(authenticationManager()))
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS); // *set ให้เป็น
	}
	  
	@Bean
	UsernamePasswordAuthenticationFilter authenticationFilter() throws Exception {// * สร้าง JWT กันนนน
		final UsernamePasswordAuthenticationFilter filter = new JWTAuthenticationFilter(authenticationManager());
		filter.setAuthenticationManager(authenticationManager());
		return filter;
	}

}
