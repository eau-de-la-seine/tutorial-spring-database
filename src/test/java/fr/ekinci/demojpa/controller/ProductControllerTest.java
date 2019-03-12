package fr.ekinci.demojpa.controller;

import fr.ekinci.demojpa.DemoJpaApplication;
import fr.ekinci.demojpa.configuration.DatabaseConfiguration;
import fr.ekinci.demojpa.service.ProductDAOviaJDBC;
import fr.ekinci.demojpa.service.ProductDAOviaJdbcTemplate;
import fr.ekinci.demojpa.service.ProductDAOviaJPA;
import fr.ekinci.demojpa.service.ProductDAOviaSpringData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(
initializers = {
		ConfigFileApplicationContextInitializer.class
}, classes = {
		ProductControllerTest.Context.class
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // => For Spring Data
public class ProductControllerTest {

	@Autowired
	private ProductController productController;

	private MockMvc mockMvc;

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(productController)
				.build();
	}

	@Test
	public void test_get() throws Exception {
		// GIVEN
		Long id = 1L;

		// WHEN
		MvcResult result = mockMvc.perform(get("/products/{id}", id)).andReturn();

		result.getResponse().getContentAsString();
	}

	public void test_get_all() throws Exception {
		mockMvc.perform(get("/products"));
	}

	public void test_post() throws Exception {
		mockMvc.perform(post("/products"));
	}

	@Import(value = {
			DemoJpaApplication.class,
			ProductController.class,
			ProductDAOviaJDBC.class,
			ProductDAOviaJdbcTemplate.class,
			ProductDAOviaJPA.class,
			ProductDAOviaSpringData.class,
			DatabaseConfiguration.class
	})
	@DataJpaTest
	public static class Context {

	}
}
