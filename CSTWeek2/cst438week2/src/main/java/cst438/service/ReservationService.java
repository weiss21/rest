package cst438.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cst438.domain.CityWeather;
import cst438.domain.Flight;
import cst438.domain.FlightRepository;
import cst438.domain.Reservation;
import cst438.domain.ReservationRepository;

@Service
public class ReservationService {
	
	private static final Logger log = LoggerFactory.getLogger(ReservationService.class);

	@Autowired
	FlightRepository flightRepository;

	@Autowired
	ReservationRepository reservationRepository;

	@Autowired
	CityWeatherService cityWeatherService;
	
	public ReservationService() {
		
	}
	
	public ReservationService(ReservationRepository reservationRepository, FlightRepository flightRepository, CityWeatherService weatherService) {
		this.reservationRepository = reservationRepository;
		this.flightRepository = flightRepository;
		this.cityWeatherService = weatherService;
	}

	public Reservation createReservation(Reservation reserve) {
		// validate reservation fields
		// flightNo , fromCity, toCity, departTime, totalPrice, reservationId
		log.info("Request Reservation: "+reserve.toString());

		reserve.setId(0);

		String flightNo = reserve.getFlightNo();
		Flight flight = null;
		if (flightNo != null) flight = flightRepository.findByFlightNo(flightNo);
		if (flight == null) {
			// flightNo is missing or invalid
			reserve.setMessage("flightNo is not valid. "+flightNo);
			log.info("Request Not Processed: "+reserve.toString());
			return reserve;
		}

		// if fromCity and toCity are present in reservation, they must
		// match the flightNo
		if (reserve.getFromCity() != null && reserve.getToCity() != null &&
				((!flight.getFromCity().equals(reserve.getFromCity())) ||
				(!flight.getToCity().equals(reserve.getToCity())))) {
			reserve.setMessage("fromCity, toCity don't match flightNo.");
			log.info("Request Not Processed: "+reserve.toString());
			return reserve;
		}
		if (reserve.getFromCity() == null || reserve.getToCity() == null) {
			reserve.setToCity(flight.getToCity());
			reserve.setFromCity(flight.getFromCity());
		}

		// update departure time and price based on current flight information
		reserve.setDepartTime(flight.getDepartTime());
		 
		reserve.setTotalPrice(reserve.getNumTickets() * flight.getPrice());

		// save the reservation
		// this will generate and set the reservation id
		Reservation newReservation = reservationRepository.save(reserve);
		
		// get Weather for destination city

		CityWeather weather = cityWeatherService.getWeather(flight.getToCity());
		newReservation.setWeather(weather);
		newReservation.setMessage("OK");
		
		log.info("Create Reservation: "+newReservation.toString());
		
		// returned updated Reservation to caller
		return newReservation;
	}

	public boolean deleteReservation(int id) {
		Reservation r = reservationRepository.findById(id);
		if (r !=null) {
			reservationRepository.delete(r);
			log.info("Reservation deleted: "+id);
			return true;
		} else {
			log.info("Reservation delete failed. Not found. "+id);
			return false;
		}
	}

	public Reservation getReservation(int id) {
		log.info("Reservation retrieve: "+id);
		Reservation r = reservationRepository.findById(id);
		if (r != null) {
			CityWeather weather = cityWeatherService.getWeather(r.getToCity());
			r.setWeather(weather);
			r.setMessage("OK");
			log.info("Reservation retrieved: "+r.toString());
			return r;
		} else {
			log.info("Reservation retrieve failed. Not found.: "+id);
			return null;
		}
	}
	
	public List<Reservation> getReservationsForUserName(String userName){
		List<Reservation> reservations =  reservationRepository.findByUserName(userName);
		for (Reservation r: reservations) {
			CityWeather weather = cityWeatherService.getWeather(r.getToCity());
			r.setWeather(weather);
			r.setMessage("OK");
		}
		log.info("Reservations for userName: "+userName+" noRervations returned="+reservations.size());
		return reservations;
	}

}
