package cst438.domain;

public class CityWeather {
	
	private String cityName;
	private double temp;
	private String condition;
	
	public CityWeather(String cityName, double temp, String condition) {
		super();
		this.cityName = cityName;
		this.temp = temp;
		this.condition = condition;
	}

	public String getCityName() {
		return cityName;
	}

	public double getTemp() {
		return temp;
	}

	public String getCondition() {
		return condition;
	}

	@Override
	public String toString() {
		return "CityWeather [cityName=" + cityName + ", temp=" + temp + ", condition=" + condition + "]";
	}

}
