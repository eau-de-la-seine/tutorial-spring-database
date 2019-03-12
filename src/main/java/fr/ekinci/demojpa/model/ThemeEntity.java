package fr.ekinci.demojpa.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * SALLE_DE_BAIN
 * SALON
 * CUISINE
 * CHAMBRE
 */
@Entity
@Table(name = "themes")
public class ThemeEntity {
	@Id
	private Long id;

	private String name;

	@OneToOne(mappedBy = "theme")
	private ProductEntity product;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
