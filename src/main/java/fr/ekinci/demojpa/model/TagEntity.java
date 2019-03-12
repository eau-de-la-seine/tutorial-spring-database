package fr.ekinci.demojpa.model;

import javax.persistence.*;

@Entity
@Table(name = "tags")
public class TagEntity {

	@Id
	private Long id;

	private String name;

	@ManyToOne
	@JoinColumn(
			name = "fk_id_product",
			referencedColumnName = "id"
	)
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
