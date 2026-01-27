package com.flowops.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flowops.auth.dto.AuthResponse;
import com.flowops.auth.dto.LoginRequest;
import com.flowops.auth.security.JwtUtil;
import com.flowops.auth.user.User;
import com.flowops.auth.user.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;

    // REGISTER
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
       
        if (user.getRole() == null) {  //prevents null pointer Exception
            user.setRole("USER");
        }
        
        return userRepository.save(user);
    }

    // LOGIN
    public AuthResponse login(LoginRequest request) {
    	
    	
    	User user = userRepository.findByEmail(request.getEmail())
    			.orElseThrow(() -> new RuntimeException("User not found"));
    	
    	if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
    		throw new RuntimeException("Invalid credentials");
    	}
    	
    	String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
    	
    	return new AuthResponse(token);
    }
 
}
