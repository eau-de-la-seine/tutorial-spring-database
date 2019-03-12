package fr.ekinci.demojpa.model;

import javax.persistence.*;

/**
 CREATE TABLE tags (
 	id integer,
 	name varchar,
 	CONSTRAINT pk_tags PRIMARY KEY(id)
 );

 CREATE SEQUENCE sequence_tags;
 ALTER TABLE tags ALTER COLUMN id SET DEFAULT nextval('sequence_tags');
 */
@Entity
@Table(name = "tags")
public class TagEntity {

	@Id
	@GeneratedValue(generator = "sequence_tags")
	private Long id;

	private String name;

	/**
	 * SELECT * FROM
	 * tags
	 * INNER JOIN products
	 * ON tags.fk_id_product = products.id
	 */
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
