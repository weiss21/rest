package cst438.domain;

import org.springframework.data.repository.CrudRepository;

public interface FlightRepository extends CrudRepository<Flight, Long> {
	
	 Flight findByFlightNo(String flightNo);

}
