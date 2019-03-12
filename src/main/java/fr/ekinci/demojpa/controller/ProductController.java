package fr.ekinci.demojpa.controller;

import fr.ekinci.demojpa.model.ProductEntity;
import fr.ekinci.demojpa.service.IProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;

/**
 * TODO: A faire: get all, put, delete
 */
@RestController
public class ProductController {

	private final IProductDAO productService;

	@Autowired
	public ProductController(
		IProductDAO productService
	) {
		this.productService = productService;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/products")
	public ProductEntity post(@RequestBody ProductEntity product) throws SQLException {
		return productService.insert(product);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/products/{id}")
	public ProductEntity get(@PathVariable("id") Long id) throws SQLException {
		ProductEntity product = new ProductEntity();
		product.setId(id);
		return productService.select(product);
	}
}
