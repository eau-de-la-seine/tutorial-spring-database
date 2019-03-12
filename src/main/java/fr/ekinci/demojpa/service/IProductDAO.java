package fr.ekinci.demojpa.service;

import fr.ekinci.demojpa.model.ProductEntity;

import java.sql.SQLException;
import java.util.List;

public interface IProductDAO {
	ProductEntity insert(ProductEntity product) throws SQLException;
	void update(ProductEntity product) throws SQLException;
	void delete(ProductEntity product) throws SQLException;
	ProductEntity select(ProductEntity product) throws SQLException;
	List<ProductEntity> selectAll() throws SQLException;
}
