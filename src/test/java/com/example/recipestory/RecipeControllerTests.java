package com.example.recipestory;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.example.recipestory.controller.RecipeController;
import com.example.recipestory.datatransferobj.RecipeDto;
import com.example.recipestory.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * RecipeControllerのエンドポイントが叩けられるかどうかのテスト
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "local.api.url.template=http://localhost:%d/recipes/%s" })
public class RecipeControllerTests {

	@InjectMocks
	RecipeController recipeController;

	@Mock
	RecipeService mockRecipeService;

	@Autowired
	WebApplicationContext wac;

	@LocalServerPort
	int port;

	@Value("${local.api.url.template}")
	String urlTemplate;

	MockMvc mockMvc;
	ObjectMapper jsonMapper = new ObjectMapper();

	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
	}

	/**
	 * 全件検索できること
	 */
	@Test
	public void getAllRecords() throws Exception {
		// setup
		List<RecipeDto> expectedRecipe = (List<RecipeDto>) TestObjectRepo.TestObject.ALL_RECIPES.data;
		when(mockRecipeService.getAllRecipes()).thenReturn(expectedRecipe);

		String requestUrl = String.format(urlTemplate, port, "");
		String expected = new String(Files.readAllBytes(Paths.get("allRecipesResponse.json")));

		// act
		mockMvc.perform(MockMvcRequestBuilders.get(requestUrl))

				// verify
				.andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(content().json(expected));
		verify(mockRecipeService).getAllRecipes();
	}

}
