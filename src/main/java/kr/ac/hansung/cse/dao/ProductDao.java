package kr.ac.hansung.cse.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kr.ac.hansung.cse.model.Product;

@Repository
public class ProductDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Product> getProducts() {
		String sqlStatement = "select * from product";
		return jdbcTemplate.query(sqlStatement, new RowMapper<Product>() {

			@Override
			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
				Product product = new Product();

				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setCategory(rs.getString("category"));
				product.setManufacturer(rs.getString("manufacturer"));
				product.setUnitInStock(rs.getInt("unitInStock"));
				product.setDescription(rs.getString("description"));
				product.setPrice(rs.getInt("price"));
				return product;
			}

		});
	}

	public boolean addProduct(Product product) {

		String name = product.getName();
		String category = product.getCategory();
		int price = product.getPrice();
		String manufacturer = product.getManufacturer();
		int unitInStock = product.getUnitInStock();
		String description = product.getDescription();

		String sql = "insert into product (name, category, manufacturer, price, unitInStock, description)"
				+ " values(?,?,?,?,?,?) ";
		return (jdbcTemplate.update(sql,
				new Object[] { name, category, manufacturer, price, unitInStock, description }) == 1);

	}

	public boolean deleteProduct(int id) {

		String sql = "delete from product where id = ?";
		return (jdbcTemplate.update(sql, new Object[] { id }) == 1);

	}

	public Product getProductById(int id) {
		
		String sqlStatement = "select * from product where id = ?";
		return jdbcTemplate.queryForObject(sqlStatement, new Object[] {id}, 
				new RowMapper<Product>() {

			@Override
			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
				Product product = new Product();

				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setCategory(rs.getString("category"));
				product.setManufacturer(rs.getString("manufacturer"));
				product.setUnitInStock(rs.getInt("unitInStock"));
				product.setDescription(rs.getString("description"));
				product.setPrice(rs.getInt("price"));
				
				return product;
			}

		});
	}

	public boolean updateProduct(Product product) {
		
		int id = product.getId();
		String name = product.getName();
		String category = product.getCategory();
		int price = product.getPrice();
		String manufacturer = product.getManufacturer();
		int unitInStock = product.getUnitInStock();
		String description = product.getDescription();

		String sql = "update product set name = ?, category = ?, manufacturer = ?, price = ?, unitInStock = ? , description = ?"
				+ " where id = ?";
		return (jdbcTemplate.update(sql,
				new Object[] { name, category, manufacturer, price, unitInStock, description, id }) == 1);

	}

}
