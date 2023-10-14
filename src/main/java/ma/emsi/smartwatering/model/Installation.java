package ma.emsi.smartwatering.model;

import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity  @Data @NoArgsConstructor @AllArgsConstructor
public class Installation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private Date dateDebut;
	private Date dateFin;
	@ManyToOne
    @JoinColumn(name="boitier_id")
	private Boitier boitier;
	
	
	public void desinstaller() {
		dateFin = new Date();
	}
}
