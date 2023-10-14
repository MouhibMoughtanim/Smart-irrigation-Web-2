package ma.emsi.smartwatering.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity  @Data @NoArgsConstructor @AllArgsConstructor
public class AppUser {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO	)
	private long id;
	private String username;
	private String address;
	private String phone;
	private String password;
	private String role;
	
	@OneToMany
	@JoinColumn(name="user_id")
	private List<EspaceVert> espacesVerts;
	
	
	
}
