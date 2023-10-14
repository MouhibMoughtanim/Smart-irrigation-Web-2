package ma.emsi.smartwatering.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity  @Data @NoArgsConstructor @AllArgsConstructor
public class EspaceVert {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO	)
	private long id;
	private String libelle;
	
    @Column(name = "photo")
	private String image;
	
	@OneToMany
	@JoinColumn(name="espace_id", nullable = true)
	private List<Zone> zones;
	
	public boolean equals(EspaceVert e) {
		 
        if (e.getId() == this.getId()) {
            return true;
        }
		return false;
    }
}
