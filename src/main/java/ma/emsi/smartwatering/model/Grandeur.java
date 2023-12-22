package ma.emsi.smartwatering.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grandeur {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private Double temperature;
	private Double humidity;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime; // Use Date for timestamp

	@ManyToOne // Many Grandeur instances can be associated with one Zone
	@JsonIgnore
	@JoinColumn(name = "zone_id") // Specify the foreign key column
	private Zone zone;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Double getHumidity() {
		return humidity;
	}

	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}
	@Override
	public String toString() {
		return "gredsf";
	}

}
