package ma.emsi.smartwatering.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity  @Data @NoArgsConstructor @AllArgsConstructor
public class SolType {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
}
