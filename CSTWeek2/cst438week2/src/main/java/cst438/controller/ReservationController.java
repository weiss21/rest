package cst438.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cst438.domain.Reservation;
import cst438.service.ReservationService;

@RestController
public class ReservationController {
	
	@Autowired
	ReservationService reservationService;
	

	@GetMapping("/reservations/{id}")
	public ResponseEntity<Reservation> getReservationById(@PathVariable("id") int id) {
		Reservation reserve = reservationService.getReservation(id);
		if (reserve == null) {
			return new ResponseEntity<Reservation>( HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Reservation>(reserve, HttpStatus.OK);
	}
	
	@GetMapping("/reservations")
	public ResponseEntity<List<Reservation>> getReservationForUserName( @RequestParam("name") String userName){
		List<Reservation> reservations =  reservationService.getReservationsForUserName(userName); 
		return new ResponseEntity<List<Reservation>>( reservations, HttpStatus.OK);
	}
	
	@PostMapping("/reservations")
	public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation ) {
		// attempt to save reservation to database
		Reservation newReservation = reservationService.createReservation(reservation);
		// return updated reservation to client
		if (newReservation.getMessage().equals("OK")) {
			return new ResponseEntity<Reservation>(newReservation, HttpStatus.OK);
		} else {
			return new ResponseEntity<Reservation>(newReservation, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@DeleteMapping("/reservations/{id}")
	public ResponseEntity<Reservation> deleteReseration(@PathVariable("id") int id) {
		boolean result = reservationService.deleteReservation(id);
		if (result) {
			return new ResponseEntity<Reservation>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Reservation>(HttpStatus.NOT_FOUND);
		}
	}

}
