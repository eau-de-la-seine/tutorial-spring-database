package fr.ekinci.demojpa.service;

import fr.ekinci.demojpa.model.ProductEntity;
import fr.ekinci.demojpa.repository.ProductRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Profile("springData")
public class ProductDAOviaSpringData implements IProductDAO {

	private final ProductRepository productRepository;

	public ProductDAOviaSpringData(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public ProductEntity insert(ProductEntity product){
		return productRepository.save(product);
	}

	@Override
	public void update(ProductEntity product){
		productRepository.save(product);
	}

	@Override
	public void delete(ProductEntity product){
		productRepository.delete(product);
	}

	@Override
	public ProductEntity select(ProductEntity product){
		return productRepository.findById(product.getId()).orElse(null);
	}

	@Override
	public List<ProductEntity> selectAll(){
		return (List<ProductEntity>) productRepository.findAll();
	}
}