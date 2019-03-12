package fr.ekinci.demojpa.service;

import fr.ekinci.demojpa.model.ProductEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("jdbc")
public class ProductDAOviaJDBC implements IProductDAO {

	private final String SQL_INSERT;
	private final String SQL_UPDATE;
	private final String SQL_DELETE;
	private final String SQL_SELECT;
	private final String SQL_SELECT_ALL;
	private final DataSource dataSource;

	public ProductDAOviaJDBC(
			DataSource dataSource,
			@Value("${tp.jdbc.SQL_INSERT}") String sqlInsert,
			@Value("${tp.jdbc.SQL_UPDATE}") String sqlUpdate,
			@Value("${tp.jdbc.SQL_DELETE}") String sqlDelete,
			@Value("${tp.jdbc.SQL_SELECT}") String sqlSelect,
			@Value("${tp.jdbc.SQL_SELECT_ALL}") String sqlSelectAll
	) {
		SQL_INSERT = sqlInsert;
		SQL_UPDATE = sqlUpdate;
		SQL_DELETE = sqlDelete;
		SQL_SELECT = sqlSelect;
		SQL_SELECT_ALL = sqlSelectAll;
		this.dataSource = dataSource;
	}

	@Override
	public ProductEntity insert(ProductEntity product) throws SQLException {
		try (
				Connection c = createConnection();
				PreparedStatement p = c.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
		) {
			p.setString(1, product.getName());
			p.setString(2, product.getUrlImage());
			p.setInt(3, product.getPrice());
			p.setString(4, product.getDescription());

			int nbInsert = p.executeUpdate();

			if (nbInsert == 1) {
				try (ResultSet generatedKeys = p.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						product.setId(generatedKeys.getLong(1));
						return product;
					}
				}
			}
		}

		return null;
	}

	@Override
	public void update(ProductEntity product) throws SQLException {
		try (
				Connection c = createConnection();
				PreparedStatement p = c.prepareStatement(SQL_UPDATE);
		) {
			p.setString(1, product.getName());
			p.setString(2, product.getUrlImage());
			p.setInt(3, product.getPrice());
			p.setString(4, product.getDescription());
			p.setLong(5, product.getId());

			p.executeUpdate();
		}
	}

	@Override
	public ProductEntity select(ProductEntity product) throws SQLException {
		try (
			Connection c = createConnection();
			PreparedStatement p = c.prepareStatement(SQL_SELECT);
		) {
			p.setLong(1, product.getId());

			try (
				ResultSet r = p.executeQuery();
			) {
				if (r.next()) {
					ProductEntity productFromDb = new ProductEntity();
					productFromDb.setId(r.getLong("id"));
					productFromDb.setName(r.getString("name"));
					productFromDb.setUrlImage(r.getString("url_image"));
					productFromDb.setPrice(r.getInt("price"));
					productFromDb.setDescription(r.getString("description"));
					return productFromDb;
				}

				return null;
			}
		}
	}

	@Override
	public List<ProductEntity> selectAll() throws SQLException {
		try (
				Connection c = createConnection();
				PreparedStatement p = c.prepareStatement(SQL_SELECT_ALL);
		) {
			try (
					ResultSet r = p.executeQuery();
			) {
				List<ProductEntity> list = new ArrayList<>();
				while (r.next()) {
					ProductEntity productFromDb = new ProductEntity();
					productFromDb.setId(r.getLong("id"));
					productFromDb.setName(r.getString(2));
					productFromDb.setUrlImage(r.getString(3));
					productFromDb.setPrice(r.getInt(4));
					productFromDb.setDescription(r.getString(5));
					list.add(productFromDb);
				}

				return list;
			}
		}
	}

	/**
	 * Syntaxe Java 6
	 *
	 * @param product
	 * @throws SQLException
	 */
	@Override
	public void delete(ProductEntity product) throws SQLException {
		Connection c = createConnection();
		PreparedStatement p = null;
		try {
			p = c.prepareStatement(SQL_DELETE);
			p.setLong(1, product.getId());
			p.executeUpdate();
		} finally {
			if (p != null) {
				p.close();
			}

			if (c != null) {
				c.close();
			}
		}
	}

	public Connection createConnection() throws SQLException {
		return dataSource.getConnection();
	}
}
