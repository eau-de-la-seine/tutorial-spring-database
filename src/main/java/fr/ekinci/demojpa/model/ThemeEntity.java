package fr.ekinci.demojpa.model;

import javax.persistence.*;

/**
 CREATE TABLE themes (
 	id integer,
 	name varchar,
 	CONSTRAINT pk_themes PRIMARY KEY(id)
 );

 CREATE SEQUENCE sequence_themes OWNED BY themes.id;
 -- Ou bien : ALTER TABLE themes ALTER COLUMN id SET DEFAULT nextval('sequence_themes');
 */
@Entity
@Table(name = "themes")
public class ThemeEntity {
	@Id
	@GeneratedValue(generator = "sequence_themes")
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
