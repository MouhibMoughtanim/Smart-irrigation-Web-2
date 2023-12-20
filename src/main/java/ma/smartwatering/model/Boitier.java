package ma.smartwatering.model;


import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity  @Data @NoArgsConstructor @AllArgsConstructor
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
}
