package cst438.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {

	Reservation findById(int id);
	
	List<Reservation> findByUserName(String userName);

}
