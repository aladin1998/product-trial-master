package com.alten.ecommerce;

import com.alten.ecommerce.entity.Product;
import com.alten.ecommerce.entity.User;
import com.alten.ecommerce.enumerations.InventoryStatus;
import com.alten.ecommerce.repository.ProductRepository;
import com.alten.ecommerce.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
class EcommerceApplicationTests {

	@LocalServerPort
	private Integer port;

	@Container
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

	private String authToken;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@BeforeAll
	static void beforeAll() {
		postgres.start();
	}

	@AfterAll
	static void afterAll() {
		postgres.stop();
	}

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
	}

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost:" + port;
		productRepository.deleteAll();
		userRepository.deleteAll();

		setupInitialData();
		authenticateAndGetToken();
	}

	private void setupInitialData() {
		// Create and save a User
		User user = new User();
		user.setEmail("admin@admin.co");
		user.setUsername("admin");
		user.setPassword("password123");
		userRepository.save(user);

		// Create and save a Product
		Product product = new Product();
		product.setCode("bib36pfvm");
		product.setName("bib36pfvm");
		product.setDescription("This is a description");
		product.setPrice(32);
		product.setCategory("Accessories");
		product.setInventoryStatus(InventoryStatus.INSTOCK);
		productRepository.save(product);
	}

	private void authenticateAndGetToken() {
		String email = "admin@admin.co";
		String password = "password123";

		String jsonBody = """
			{
				"email": "%s",
				"password": "%s"
			}
		""".formatted(email, password);

		// Perform login to get the token
		authToken = given()
				.contentType(ContentType.JSON)
				.body(jsonBody)
				.when()
				.post("/auth/token")
				.then()
				.statusCode(200) // Assumes a 200 OK response on successful authentication
				.extract()
				.body()
				.asString();

		// Ensure the token is obtained
		if (authToken == null || authToken.isEmpty()) {
			throw new RuntimeException("Failed to obtain authentication token");
		}
	}

	@Test
	void shouldReturn200WhenFetchingProductsWithValidAuthToken() {

		// Ensure auth token is not null
		assertNotNull(authToken);


		// Assuming 1 product is saved in the DB
		given()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + authToken)
				.when()
				.get("/products")
				.then()
				.statusCode(200)
				.body(".", hasSize(1));
	}

}