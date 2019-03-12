package fr.ekinci.demojpa.service;

import fr.ekinci.demojpa.model.ProductEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Profile("jdbcTemplate")
public class ProductDAOviaJdbcTemplate implements IProductDAO {

	private final JdbcTemplate jdbcTemplate;
	private final String SQL_UPDATE;
	private final String SQL_DELETE;
	private final String SQL_SELECT;
	private final String SQL_SELECT_ALL;

	public ProductDAOviaJdbcTemplate(
			JdbcTemplate jdbcTemplate,
			@Value("${tp.jdbc.SQL_UPDATE}") String sqlUpdate,
			@Value("${tp.jdbc.SQL_DELETE}") String sqlDelete,
			@Value("${tp.jdbc.SQL_SELECT}") String sqlSelect,
			@Value("${tp.jdbc.SQL_SELECT_ALL}") String sqlSelectAll
	) {
		this.jdbcTemplate = jdbcTemplate;
		this.SQL_UPDATE = sqlUpdate;
		this.SQL_DELETE = sqlDelete;
		this.SQL_SELECT = sqlSelect;
		this.SQL_SELECT_ALL = sqlSelectAll;
	}

	@Override
	public ProductEntity insert(ProductEntity product) throws SQLException {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
			.withTableName("product")
			.usingGeneratedKeyColumns(
			/* PRIMARY_KEYS */ "id");

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", product.getName());
		parameters.put("url_image", product.getUrlImage());
		parameters.put("price", product.getPrice());
		parameters.put("description", product.getDescription());

		product.setId(
			(Long) jdbcInsert.executeAndReturnKey(parameters)
		);

		return product;
	}

	@Override
	public void update(ProductEntity product) throws SQLException {
		jdbcTemplate.update(SQL_UPDATE,
				product.getName(),
				product.getUrlImage(),
				product.getPrice(),
				product.getDescription(),
				product.getId());
	}

	@Override
	public void delete(ProductEntity product) throws SQLException {
		jdbcTemplate.update(
				SQL_DELETE,
				product.getId());
	}

	@Override
	public ProductEntity select(ProductEntity product) throws SQLException {
		return jdbcTemplate.queryForObject(
				SQL_SELECT,
				new ProductRowMapper(),
				product.getId());
	}

	@Override
	public List<ProductEntity> selectAll() throws SQLException {
		return jdbcTemplate.query(
				SQL_SELECT_ALL,
				new ProductRowMapper());
	}

	public static class ProductRowMapper implements RowMapper<ProductEntity> {
		@Override
		public ProductEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProductEntity product = new ProductEntity();
			product.setId(rs.getLong("id"));
			product.setName(rs.getString("name"));
			product.setUrlImage(rs.getString("url_image"));
			product.setPrice(rs.getInt("price"));
			product.setDescription(rs.getString("description"));
			return product;
		}
	}
}
