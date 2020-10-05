package com.futsal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.futsal.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
 Optional<User> findByUsername(String username);
 
 Boolean existsByUsername(String username);
 
 Boolean existsByPhone(Long phone);
 
 @Query(value = "select u.username,r.startingTime,r.endTime,r.date from User as u , Reservation as r where u.Reservation = r.user_id", nativeQuery = true)
	List<User> getAllReservation();
	
}
