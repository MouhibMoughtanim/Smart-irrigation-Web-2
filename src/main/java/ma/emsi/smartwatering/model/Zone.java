package ma.emsi.smartwatering.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity  @Data @NoArgsConstructor @AllArgsConstructor
public class Zone {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String libelle;

	@Column(nullable = true)
	private float superficie;

	@Lob
	@Column(name = "photo", nullable = true)
	private String image;

	@ManyToOne
	@JoinColumn(name="type_id", nullable = true)
	private SolType type;

	@OneToMany
	private List<Plantage> plantages;

	@OneToMany
	private List<Arrosage> arrosages;

	@OneToMany
	private List<Installation> installations;

	@OneToMany
	@JsonIgnoreProperties("zone")
	@JoinColumn(name="zone_id")
	private List<Grandeur> grandeurs;

	@OneToMany
	@JoinColumn(name="zone_id")
	private List<Notification> notifications;


	public Installation getUActualBoitier() {
		if (this.installations.isEmpty()) {
			System.out.println("No installations found for Zone " + this.id);
			return null;
		}

		Installation lastInstallation = this.installations.get(this.installations.size() - 1);

		if (lastInstallation.getDateFin() == null) {
			return lastInstallation;
		} else {
			System.out.println("Last installation for Zone " + this.id + " has a non-null dateFin");
		}

		return null;
	}

	@Override
	public String toString() {
		return "Zone{id=" + id + ", libelle='" + libelle + '\'' + ", superficie=" + superficie + '}';
	}


}
