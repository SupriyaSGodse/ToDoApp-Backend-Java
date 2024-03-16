package com.example.todo.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.todo.dto.JwtAuthResponse;
import com.example.todo.dto.LoginDto;
import com.example.todo.dto.RegisterDto;
import com.example.todo.entity.Role;
import com.example.todo.entity.User;
import com.example.todo.exceptions.TodoAPIException;
import com.example.todo.repository.RoleRepository;
import com.example.todo.repository.UserRepository;
import com.example.todo.security.JwtTokenProvider;
import com.example.todo.service.AuthService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService{

	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;
	
	@Override
	public String register(RegisterDto registerDto) {
		// TODO Auto-generated method stub
		
		if(userRepository.existsByUsername(registerDto.getUsername())) {
			throw new TodoAPIException(HttpStatus.BAD_REQUEST,"Username already exists");
		}
		
		if(userRepository.existsByEmail(registerDto.getEmail())) {
			throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Email id already exists");
		}
		
		User user =new User();
		
		user.setName(registerDto.getName());
		user.setUsername(registerDto.getUsername());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		
		Set<Role> roles= new HashSet<>();
		Role userRole= roleRepository.findByName("ROLE_USER");
		
		roles.add(userRole);
		
		user.setRoles(roles);
		
		userRepository.save(user);
		return "User Registered Successfully";
	}

	@Override
	public JwtAuthResponse login(LoginDto loginDto) {
		 
		System.out.println(loginDto.getUsernameOrEmail());
		
		  Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
	                loginDto.getUsernameOrEmail(),
	                loginDto.getPassword()
	        ));

	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        
	        String token = jwtTokenProvider.generateToken(authentication);
	      
	        Optional<User> userOptional  = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail());
	        
	        String role= null;
	        if(userOptional.isPresent()) {
	        	User loggedInUser = userOptional.get();
	        	Optional<Role> optionalRole= loggedInUser.getRoles().stream().findFirst();
	        	
	        	if(optionalRole.isPresent()) {
	        		Role userRole = optionalRole.get();
	        		role = userRole.getName();
	        	}
	        }
	        
	       JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
	       jwtAuthResponse.setRole(role);
	       jwtAuthResponse.setAccessToken(token);
	       
		return jwtAuthResponse;
	}

}
