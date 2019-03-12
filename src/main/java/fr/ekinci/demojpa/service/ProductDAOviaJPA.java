package fr.ekinci.demojpa.service;

import fr.ekinci.demojpa.configuration.DatabaseConfiguration;
import fr.ekinci.demojpa.model.ProductEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.SQLException;
import java.util.List;

@Repository
@Profile("jpa")
public class ProductDAOviaJPA implements IProductDAO {

	@PersistenceContext(unitName = DatabaseConfiguration.JPA_PERSISTANCE_UNIT_NAME)
	private EntityManager em;

	@Override
	public ProductEntity insert(ProductEntity product) {
		product.setId(null);
		return em.merge(product);
	}

	@Override
	public void update(ProductEntity product) {
		em.merge(product);
	}

	@Override
	public void delete(ProductEntity product) throws SQLException {
		em.remove(product);
	}

	@Override
	public ProductEntity select(ProductEntity product) {
		return em.find(ProductEntity.class, product.getId());
	}

	@Override
	public List<ProductEntity> selectAll() {
		return em.createQuery("SELECT p FROM Product p", ProductEntity.class).getResultList();
	}
}
