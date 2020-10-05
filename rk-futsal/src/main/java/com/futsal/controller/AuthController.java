package com.futsal.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.futsal.model.ERole;
import com.futsal.model.Role;
import com.futsal.model.User;
import com.futsal.payload.request.LoginRequest;
import com.futsal.payload.request.SignupRequest;
import com.futsal.payload.response.JwtResponse;
import com.futsal.payload.response.MessageResponse;
import com.futsal.repository.RoleRepository;
import com.futsal.repository.UserRepository;
import com.futsal.security.jwt.JwtUtils;
import com.futsal.services.UserDetailsImpl;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
				
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails =(UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(new JwtResponse(
				jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getPhone(), roles
				));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser( @RequestBody SignupRequest signUpRequest){
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}
		
		if (userRepository.existsByPhone(signUpRequest.getPhone())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: phone number is already in use!"));
		}
		
		// create new user account
		
		User user = new User(signUpRequest.getUsername(),
						     signUpRequest.getPhone(),
						     encoder.encode(signUpRequest.getPassword()));
		
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		
		if(strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			
			roles.add(userRole);
		}
		
		user.setRoles(roles);
		userRepository.save(user);
		
		return ResponseEntity.ok(new MessageResponse("User registered successfully !!"));
		
	}
	
	@GetMapping("/user/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable(value = "username") String username) {
		
        User user = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("Error: username not found"));
		
		return ResponseEntity.ok().body(user);
	}
	
	
}
