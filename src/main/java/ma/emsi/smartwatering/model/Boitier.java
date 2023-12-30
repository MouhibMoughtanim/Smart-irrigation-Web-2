package ma.emsi.smartwatering.model;


import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity  @Data @NoArgsConstructor @AllArgsConstructor @ToString(exclude = "connections")
public class Boitier {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String ref;
	private String type;
	private String code;
	private String image;

	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="boitier_id")

	private List<Connection> connections;
	@Override
	public String toString() {
		return "Boitier{" +
				"id=" + id +
				", ref='" + ref + '\'' +
				", type='" + type + '\'' +
				", code='" + code + '\'' +
				", image='" + image + '\'' +
				", connections=" + (connections != null ? connections.toString() : null) +
				'}';
	}
}
