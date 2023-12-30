package ma.emsi.smartwatering.model;

import java.time.ZonedDateTime;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity  @Data @NoArgsConstructor @AllArgsConstructor @ToString(exclude = "capteur") // Exclude capteur to avoid potential infinite recursion

public class Connection {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private boolean fonctionnel;
	private String branche;
	@JsonIgnore

	@ManyToOne
	@JoinColumn(name = "capteur_id")
	private Capteur capteur;

	@Override
	public String toString() {
		return "Connection{" +
				"id=" + id +
				", fonctionnel=" + fonctionnel +
				", branche='" + branche + '\'' +
				", capteur=" + (capteur != null ? capteur.getId() : null) +
				'}';
	}

}
