package com.blogbackend.blogbackend.security;

import static com.blogbackend.blogbackend.security.SecurityConstants.SECRET_KEY;
import static com.blogbackend.blogbackend.security.SecurityConstants.EXPIRATION_TIME;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.blogbackend.blogbackend.controller.request.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {// *เป็นตัวในการจัดการ login
		this.authenticationManager = authenticationManager;
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/auth/login", "POST"));
	}

	// *เมื่อ path match แล้วจะทำตรงนี้
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			UserRequest userRequest = new ObjectMapper().readValue(request.getInputStream(), UserRequest.class);
			// *ต้อง return obj ออกไป
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword(),new ArrayList<>()));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	// *เข้ามาเมื่อ Authen ผ่าน Gen JWT !!
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		if (authResult.getPrincipal() != null) {// *ทำการ catch //*หลังจาก catch จะได้ username
			org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult
					.getPrincipal();
			// * ทำ JWT ..username **ติดตั้ง Dependency****
			String username = user.getUsername();
			if (username != null && username.length() > 0) {
				Claims claims = Jwts.claims().setSubject(username);
				
				// //รูปแบบในการส่ง
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				// *สร้างตัว json string
				Map<String, Object> responseJSON = new HashMap<>();
				responseJSON.put("token", createToken(claims)); // *ต้อง ทำการ base 64 แล้วencode || สร้าง Fn//
				
				// *return ออกไป
				OutputStream out = response.getOutputStream();
				ObjectMapper mapper = new ObjectMapper();
				mapper.writerWithDefaultPrettyPrinter().writeValue(out, responseJSON);
				
				out.flush();

			}

		}
	}
	// * createToken
	private String createToken(Claims claims) {
		return Jwts.builder().setClaims(claims) // *ได้ตัว payload
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

}
