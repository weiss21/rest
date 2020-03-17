package cst438.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import cst438.domain.Reservation;
import cst438.service.ReservationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private MockMvc mvc;

    // These objects will be magically initialized by the initFields method below.
    private JacksonTester<Reservation> jsonReservationAttempt;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void createReservationOK() throws Exception {
    	// input Reservation request
    	Reservation attempt =  
    			new Reservation();
    	attempt.setDate("20200101");
    	attempt.setFlightNo("Otter 101");
    	attempt.setNumTickets(2);
    	attempt.setUserName("testUser");
    	
    	// expected Reservation to be returned
    	Reservation expected= 
 	    	   new Reservation(198113, "Otter 101", "testUser", "Monterey", "Seattle", "20200101", "0800 AM", 2, 244.02, 73, "test weather", "OK");
    	
    	// stub out the Reservation Service class 
    	 given(reservationService.createReservation(attempt))
                 .willReturn(expected);
    	 
    	// when
         MockHttpServletResponse response = mvc.perform(
                 post("/reservations").contentType(MediaType.APPLICATION_JSON)
                         .content(jsonReservationAttempt.write(attempt).getJson()))
                 .andReturn().getResponse();

         // then
         assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
         assertThat(response.getContentAsString()).isEqualTo(
                 jsonReservationAttempt.write(expected).getJson());
    }

    @Test
    public void createReservationBAD() throws Exception {
    	// input Reservation request
    	Reservation attempt =  
    			new Reservation();
    	attempt.setDate("20200101");
    	attempt.setFlightNo("Otter 000");
    	attempt.setNumTickets(2);
    	attempt.setUserName("testUser");
    	
    	// expected Reservation to be returned
    	Reservation expected= new Reservation();
    	expected.setDate("20200101");
    	expected.setFlightNo("Otter 000");
    	expected.setNumTickets(2);
    	expected.setUserName("testUser");
    	expected.setMessage("flightNo is not valid. Otter 000");
 	
    	// stub out the Reservation Service class 
    	 given(reservationService.createReservation(attempt))
                 .willReturn(expected);
    	 
    	// when
         MockHttpServletResponse response = mvc.perform(
                 post("/reservations").contentType(MediaType.APPLICATION_JSON)
                         .content(jsonReservationAttempt.write(attempt).getJson()))
                 .andReturn().getResponse();

         // then
         assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
         assertThat(response.getContentAsString()).isEqualTo(
                 jsonReservationAttempt.write(expected).getJson());
    }



}