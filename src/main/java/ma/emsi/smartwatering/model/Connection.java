package ma.emsi.smartwatering.model;

import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity  @Data @NoArgsConstructor @AllArgsConstructor
public class Connection {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private boolean fonctionnel;
	private String branche;
	
	@ManyToOne
    @JoinColumn(name="capteur_id")
	private Capteur capteur;
}
