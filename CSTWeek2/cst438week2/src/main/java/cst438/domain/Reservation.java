package cst438.domain;

import javax.persistence.*;

@Entity
public class Reservation {
	
	@Id
	@GeneratedValue
	private int id;
	
	private String flightNo;
	private String userName;
	private String fromCity;
	private String toCity;
	private String date;
	private String departTime;
	private int numTickets;
	private double totalPrice;
	
	
	// transient attributes are not stored in database. 
	@Transient
	private double toCityTemp;
	@Transient
	private String toCityWeatherCondition;
	
	@Transient
	private String message;
	
	public Reservation() {
		
	}
	
	
	

	public Reservation(int id, String flightNo, String userName, String fromCity, String toCity, String date,
			String departTime, int numTickets, double totalPrice, double toCityTemp, String toCityWeatherCondition,
			String message) {
		super();
		this.id = id;
		this.flightNo = flightNo;
		this.userName = userName;
		this.fromCity = fromCity;
		this.toCity = toCity;
		this.date = date;
		this.departTime = departTime;
		this.numTickets = numTickets;
		this.totalPrice = totalPrice;
		this.toCityTemp = toCityTemp;
		this.toCityWeatherCondition = toCityWeatherCondition;
		this.message = message;
	}




	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getNumTickets() {
		return numTickets;
	}

	public void setNumTickets(int numTickets) {
		this.numTickets = numTickets;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	

	public String getFromCity() {
		return fromCity;
	}

	public void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}

	public String getToCity() {
		return toCity;
	}

	public void setToCity(String toCity) {
		this.toCity = toCity;
	}

	public String getDepartTime() {
		return departTime;
	}

	public void setDepartTime(String departTime) {
		this.departTime = departTime;
	}

	public double getToCityTemp() {
		return toCityTemp;
	}

	public void setToCityTemp(double toCityTemp) {
		this.toCityTemp = toCityTemp;
	}

	public String getToCityWeatherCondition() {
		return toCityWeatherCondition;
	}

	public void setToCityWeatherCondition(String toCityWeatherCondition) {
		this.toCityWeatherCondition = toCityWeatherCondition;
	}
	

	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setWeather(CityWeather weather) {
		if (weather == null) return;
		toCityTemp = weather.getTemp();
		toCityWeatherCondition = weather.getCondition();
	}


	@Override
	public String toString() {
		return "Reservation [id=" + id + ", flightNo=" + flightNo + ", userName=" + userName + ", fromCity=" + fromCity
				+ ", toCity=" + toCity + ", date=" + date + ", departTime=" + departTime + ", numTickets=" + numTickets
				+ ", totalPrice=" + totalPrice + ", toCityTemp=" + toCityTemp + ", toCityWeatherCondition="
				+ toCityWeatherCondition + ", message=" + message + "]";
	}


	// equals checks all fields except:  weather, message
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reservation other = (Reservation) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (departTime == null) {
			if (other.departTime != null)
				return false;
		} else if (!departTime.equals(other.departTime))
			return false;
		if (flightNo == null) {
			if (other.flightNo != null)
				return false;
		} else if (!flightNo.equals(other.flightNo))
			return false;
		if (fromCity == null) {
			if (other.fromCity != null)
				return false;
		} else if (!fromCity.equals(other.fromCity))
			return false;
		if (id != other.id)
			return false;
		if (numTickets != other.numTickets)
			return false;
		if (toCity == null) {
			if (other.toCity != null)
				return false;
		} else if (!toCity.equals(other.toCity))
			return false;
		
		if (Double.doubleToLongBits(totalPrice) != Double.doubleToLongBits(other.totalPrice))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
	


	
}
