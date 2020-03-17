package cst438.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import cst438.domain.*;

@SpringBootTest
public class ReservationServiceTest {

	    private ReservationService reservationService;  // this is class under test

	    @Mock
	    private CityWeatherService weatherService;

	    @Mock
	    private FlightRepository flightRepository;
	    
	    @Mock
	    private ReservationRepository reservationRepository;

	
	    @BeforeEach
	    public void setUpEach() {
	    	MockitoAnnotations.initMocks( this);
	    	reservationService = new ReservationService(reservationRepository, flightRepository, weatherService);
	    	
	    }

	    @Test
	    public void getReservationTest() {
	    	Reservation testReservation =   
	    			new Reservation(1, "Otter 101", "testUser", "Monterey", "Seattle", "20200101", "0800 AM", 1, 150.00, 0, "unknown", "OK");
	    	
	  
	        // mocked reservationRepository will return testRervaton when findById(1) is called
	        given(reservationRepository.findById(1)).willReturn(testReservation);
	        given(weatherService.getWeather("Seattle")).willReturn(new CityWeather("Seattle", 73, "test weather"));
	        
	        // call the reservationService
	        Reservation actual   = reservationService.getReservation(1);

	        // verify that actual Reservation returned is correct.  Uses the Reservation equals method.
	        // equals does not check weather or message.  So we do that separately.
	        assertThat( actual ).isEqualTo(testReservation);
	        assertThat( actual.getToCityTemp()).isEqualTo(73);
	        assertThat( actual.getToCityWeatherCondition()).isEqualTo("test weather");
	        assertThat( actual.getMessage()).isEqualTo("OK");
	    }

	    @Test
	    public void deleteReservationTest() {
	        // given
	    	Reservation testReservation =  
	    			new Reservation(1, "Otter 101", "testUser", "Monterey", "Seattle", "20200101", "0800 AM", 1, 150.00, 44, "cloudy", "OK");
	    	
	  
	        // given (our mocked reservation repository,  findBy(1) will return the reservation
	    	
	        given(reservationRepository.findById(1)).willReturn(testReservation);
	        
	        // when
	        boolean result = reservationService.deleteReservation(1);
	        
	        // then verify that repository delete method was called correctly
	        //  Reservation class must define equals method
	        assertThat(result).isTrue();
	        verify(reservationRepository).delete(testReservation);
	    }

	    @Test
	    public void createReservationTest() {
	    	
	    	 // create the fake data 
	    	Reservation input =  
	    			new Reservation();
	    	input.setDate("20200101");
	    	input.setFlightNo("Otter 101");
	    	input.setNumTickets(2);
	    	input.setUserName("testUser");
	    	
	    	Reservation indb =  
	    	  new Reservation(0, "Otter 101", "testUser", "Monterey", "Seattle", "20200101", "0800 AM", 2, 244.02, 0, null, null);

	    	Reservation outdb =  // same as indb, except the reservation id is updated 
	    	  new Reservation(198113, "Otter 101", "testUser", "Monterey", "Seattle", "20200101", "0800 AM", 2, 244.02, 0, null, null);
	    	
	    	Reservation expected= 
	    	   new Reservation(198113, "Otter 101", "testUser", "Monterey", "Seattle", "20200101", "0800 AM", 2, 244.02, 73, "test weather", "OK");
	  
	    	
	        given(flightRepository.findByFlightNo("Otter 101")).willReturn(
	        		new Flight(1, "Otter 101", "Monterey", "Seattle", "0800 AM", 122.01));
	        
	        given(weatherService.getWeather("Seattle")).willReturn(new CityWeather("Seattle", 73, "test weather"));
	        
	        given(reservationRepository.save(indb)).willReturn(outdb);
	        
	        // when
	        Reservation actual = reservationService.createReservation(input);
	        
	        // then verify actual reservation returned is as expected
	         
	        assertThat(actual).isEqualTo(expected);
	        assertThat( actual.getToCityTemp()).isEqualTo(expected.getToCityTemp());
	        assertThat( actual.getToCityWeatherCondition()).isEqualTo(expected.getToCityWeatherCondition());
	        assertThat( actual.getMessage()).isEqualTo(expected.getMessage());
	        
	    }
	    
	    @Test
	    public void getReservationsForUserTest() {
	    
	    	
	        List<Reservation> reservations = 
	                Arrays.asList(
	                		new Reservation(198113, "Otter 101", "testUser", "Monterey", "Seattle", "20200701", "0800 AM", 1, 122.01, 0, null, null),  
	    	    			new Reservation(198114, "Otter 102", "testUser", "Seattle", "Monterey", "20200702", "1300 PM", 1, 110.00, 0, null, null));
	                              
	    	
	        given(reservationRepository.findByUserName("testUser")).willReturn(reservations);
	        
	        given(weatherService.getWeather("Seattle")).willReturn(new CityWeather("Seattle", 73, "test weather"));
	        given(weatherService.getWeather("Monterey")).willReturn(new CityWeather("Monterey", 58, "test cloudy"));
	        
	
	        
	        // when
	        List<Reservation> actual = reservationService.getReservationsForUserName("testUser");
	        
	        // then verify that the weather and message info are correct.
	        assertThat(actual.size()).isEqualTo(2);
	        assertThat( actual.get(0).getToCityTemp()).isEqualTo(73);
	        assertThat( actual.get(1).getMessage()).isEqualTo("OK");
	        
	    }

	 
	}
