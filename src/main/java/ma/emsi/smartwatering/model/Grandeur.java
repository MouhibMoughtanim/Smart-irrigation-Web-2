package ma.emsi.smartwatering.model;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity  @Data @NoArgsConstructor @AllArgsConstructor
public class Grandeur {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private float valeur;
	private String type;
	private LocalDateTime date;
}
