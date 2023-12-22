package ma.smartwatering.model;



import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity  @Data @NoArgsConstructor @AllArgsConstructor
public class Capteur {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String type;
	private String image;
	private float freq;

	@OneToMany(mappedBy = "capteur",cascade = CascadeType.ALL)
	private List<Connection> connections;

	@Override
	public String toString() {
		return "Capteur{" +
				"id=" + id +
				", name='" + type + '\'' +
				// Add other fields you want to include in the string representation
				'}';
	}

}
