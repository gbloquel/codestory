package fr.gbloquel.codestory.jajascript;

import org.codehaus.jackson.annotate.JsonProperty;

import com.google.common.base.Objects;

/**
 * This class store the command.
 * @author greg
 *
 */
public class Command {

	@JsonProperty("VOL")
	private String flightID;
	

	@JsonProperty("DEPART")
	private int startTime;
	
	@JsonProperty("DUREE")
	private long ellaspedTime;
	
	@JsonProperty("PRIX")
	private long price;
	
	public String getFlightID() {
		return flightID;
	}
	
	public void setFlightID(String flightID) {
		this.flightID = flightID;
	}
	
	public int getStartTime() {
		return startTime;
	}
	
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	
	public long getEllaspedTime() {
		return ellaspedTime;
	}
	
	public void setEllaspedTime(long ellaspedTime) {
		this.ellaspedTime = ellaspedTime;
	}
	
	public long getPrice() {
		return price;
	}
	
	public void setPrice(long price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("flightID", flightID)
				.add("startTime", startTime)
				.add("ellapsedTime", ellaspedTime)
				.add("price", price)
					.toString();
	}
}
