package fr.ekinci.demojpa.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ekinci.demojpa.DemoJpaApplication;
import fr.ekinci.demojpa.configuration.DatabaseConfiguration;
import fr.ekinci.demojpa.model.ProductEntity;
import fr.ekinci.demojpa.model.ThemeEntity;
import fr.ekinci.demojpa.service.ProductDAOviaJDBC;
import fr.ekinci.demojpa.service.ProductDAOviaJPA;
import fr.ekinci.demojpa.service.ProductDAOviaJdbcTemplate;
import fr.ekinci.demojpa.service.ProductDAOviaSpringData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

	private ObjectMapper mapper = new ObjectMapper();

	private MockMvc mockMvc;

	@Before
	public void init() {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

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
	@Test
	public void test_post() throws Exception {
		// GIVEN
		String route = "/products";

		// Theme informatique
		ThemeEntity t = new ThemeEntity();
		t.setId(1L);

		ProductEntity p = new ProductEntity();
		p.setName("PC Portable");
		p.setDescription("ça sert écrire des trucs dedans");
		p.setPrice(1000);
		p.setTheme(t);

		// WHEN
		MvcResult result = mockMvc.perform(
				post(route)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(mapper.writeValueAsString(p))
		)
				.andExpect(status().isOk())
				.andReturn();
		String responseBody = result.getResponse().getContentAsString();
		ProductEntity response = mapper.readValue(responseBody, ProductEntity.class);

		// THEN
		Assert.assertEquals("Cahier", response.getName());
		Assert.assertEquals("ça sert écrire des trucs dedans", response.getDescription());
		Assert.assertEquals(1000L, response.getPrice().longValue());
		Assert.assertEquals(1L, response.getTheme().getId().longValue());
	}

	public void test_get_all() throws Exception {
		mockMvc.perform(get("/products"));
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
