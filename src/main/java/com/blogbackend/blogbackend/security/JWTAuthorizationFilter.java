package com.blogbackend.blogbackend.security;

//* string duplicate 
import static com.blogbackend.blogbackend.security.SecurityConstants.HEADER_AUTHORIZATION;
import static com.blogbackend.blogbackend.security.SecurityConstants.TOKEN_PREFIX;
import static com.blogbackend.blogbackend.security.SecurityConstants.SECRET_KEY;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) { // *ดึงมาดูว่า Token ถูกต้องหรือเปล่า
		super(authenticationManager); // *สืบทอดเสร็จสิ้น
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
		// *การ set กับ filter ถ้าไม่ได้ เหน็บมาปล่อยให้ไป filter ต่อไป
		if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
			UsernamePasswordAuthenticationToken authentication = getAuthentication(authorizationHeader);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		// *ถ้าไม่ใช่ ส่งไปต่อ ในที่นี้ จะถูกส่ง ไป -> 401 || แต่ถ้ามี Bearer ต้อง check
		// jwtต่อ -> 401 |หรือถ้าถูก 201
		chain.doFilter(request, response);
	}

	// * แกะข้อมูลออกมา
	private UsernamePasswordAuthenticationToken getAuthentication(String jwt) {// *ส่ง JWT เข้ามา
		Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt.replace(TOKEN_PREFIX, "")).getBody();

		// * get string token มี username มั้ย
		String username = claims.getSubject();
		if (username == null) {
			return null;
		}
		
		return new UsernamePasswordAuthenticationToken(username, null,null);

	}

}
