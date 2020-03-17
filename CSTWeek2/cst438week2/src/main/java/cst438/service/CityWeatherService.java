package cst438.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;

import cst438.domain.CityWeather;

@Service
public class CityWeatherService {
	
	private static final Logger log = LoggerFactory.getLogger(CityWeatherService.class);
	private RestTemplate restTemplate;
	private String weatherUrl;
	private String apiKey;
	
	public CityWeatherService(
			@Value("${weather.url}") final String weatherUrl, 
			@Value("${weather.apikey}") final String apiKey ) {
		this.restTemplate = new RestTemplate();
		this.weatherUrl = weatherUrl;
		this.apiKey = apiKey; 
	}
	
	public  CityWeather getWeather(String cityName) {
		ResponseEntity<JsonNode> response = restTemplate.getForEntity(
				weatherUrl + "?q=" + cityName + "&appid=" + apiKey,
				JsonNode.class);
		JsonNode json = response.getBody();
		log.info("Status code from weather server:" + response.getStatusCodeValue());
		double temp = json.get("main").get("temp").asDouble();
		temp = Math.round((temp - 273.15) * 9.0/5.0 + 32.0);
		String condition = json.get("weather").get(0).get("description").asText();
	
		return new CityWeather(cityName, temp, condition );
	}

}
