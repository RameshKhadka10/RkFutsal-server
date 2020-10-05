package com.futsal.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.futsal.exception.UserNotFoundException;
import com.futsal.model.Reservation;
import com.futsal.model.User;
import com.futsal.payload.request.ReservationRequest;
import com.futsal.payload.response.MessageResponse;
import com.futsal.repository.ReservationRepository;
import com.futsal.repository.UserRepository;



@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/reservation")
public class ReserveController {
	
	@Autowired
	ReservationRepository reserveRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/reserve")
	public ResponseEntity<?> reserveFutsal(@Valid @RequestBody ReservationRequest reserveRequest){
		if(!(reserveRequest.getUsername() == null)) {
			
			Reservation reservation = new Reservation(reserveRequest.getStartingTime(),
														reserveRequest.getEndTime(),
														reserveRequest.getDate());
			
			User user = userRepository.findByUsername(reserveRequest.getUsername()).orElseThrow( () -> new RuntimeException("Error: Username not found"));
			
			reservation.setUser(user);
			reserveRepository.save(reservation);
			return ResponseEntity.ok(new MessageResponse("Reservation successfull !!"));
		}
		
		return ResponseEntity.badRequest().body(new MessageResponse("Error: cannot make reservation !!"));
		
	}
	
	@GetMapping("/reserve/{username}")
	public ResponseEntity<User> getReservationByUsername(@PathVariable(value = "username") String username) throws UserNotFoundException {
		
		
		User user = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("Error: username not found"));
		
		return ResponseEntity.ok().body(user);
	}
	
	//get all reservations 
	
	@GetMapping("/all")
	public List<User> getAllReservations() {
		List<User> user=  userRepository.findAll();
		
		
		return user;
	}


}
