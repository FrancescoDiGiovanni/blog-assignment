package ch.qa.blog_assignment;

import ch.qa.blog_assignment.DTOs.AuthorDTO;
import ch.qa.blog_assignment.DTOs.CategoryDTO;
import ch.qa.blog_assignment.DTOs.PostDTO;
import ch.qa.blog_assignment.utilities.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BlogAssignmentApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	ObjectMapper mapper = new ObjectMapper();

	@Test
	void contextLoads() {
	}

	// POST CREATION TESTS
	@Test
	void givenNewPost_testPostCreation() throws Exception{
		PostDTO postDTO = new PostDTO();
		postDTO.setTitle("New Title");
		postDTO.setContent("New Content");
		postDTO.setAuthor(new AuthorDTO("john@doe.com", "John", "Doe"));
		postDTO.setImage("https://www.site.url/logo.png");

		ResultActions resultActions =
				mockMvc.perform(post("/post/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(postDTO))
				);

		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.payload.title").value("New Title"))
				.andExpect(jsonPath("$.payload.author.email").value("john@doe.com"));

	}

	// POST READ TESTS
	@Test
	void givenAllParameters_createNewPostAndlistAllPosts() throws Exception{
		createNewPost();

		String title = "New test";
		String category = "CATEGORY";
		String tags = "SPORT";
		ResultActions resultActions =
				mockMvc.perform(get("/post/?title="+title+"&category="+category+"&tags="+tags)
						.contentType(MediaType.APPLICATION_JSON)
				);

		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.payload").isArray());
	}

	@Test
	void noParameters_createNewPostAndListAllPosts() throws Exception{
		createNewPost();

		ResultActions resultActions =
				mockMvc.perform(get("/post/")
						.contentType(MediaType.APPLICATION_JSON)
				);

		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.payload").isArray());
	}

	@Test
	void given1Parameter_createNewPostAndListAllPosts() throws Exception{
		createNewPost();

		String title = "New test";

		ResultActions resultActions =
				mockMvc.perform(get("/post/?title="+title));

		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.payload").isArray());
	}

	// POST DELETE TESTS
	@Test
	void createNewPost_deletePost() throws Exception {
		int id = createNewPost().getId();

		ResultActions resultActions =
				mockMvc.perform(delete("/post/"+id)
						.header("X-User", "admin"));

		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true));

	}

	@Test
	void deleteNotExistingPost() throws Exception {
		ResultActions resultActions =
				mockMvc.perform(delete("/post/0")
						.header("X-User", "admin"));

		resultActions
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.success").value(false));

	}

	//POST UPDATE TESTS
	@Test
	void createNewPost_fullyUpdate() throws Exception {
		int id = createNewPost().getId();

		PostDTO updatedPost = new PostDTO();
		updatedPost.setTitle("new title updated");
		updatedPost.setContent("new content updated");
		updatedPost.setAuthor(new AuthorDTO("mike@doe.com", "mike", "Doe"));
		updatedPost.setImage("https://www.newsite.url/logo.png");

		ResultActions resultActions =
				mockMvc.perform(put("/post/"+id)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(updatedPost)));

		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true));

	}

	@Test
	void createNewPost_partiallyUpdate() throws Exception {
		int id = createNewPost().getId();

		PostDTO updatedPost = new PostDTO();
		updatedPost.setTitle("new title updated");

		ResultActions resultActions =
				mockMvc.perform(put("/post/"+id)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(updatedPost)));

		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true));

	}

	@Test
	void updateNotExistingPost() throws Exception {
		PostDTO updatedPost = new PostDTO();
		updatedPost.setTitle("new title updated");
		updatedPost.setContent("new content updated");
		updatedPost.setAuthor(new AuthorDTO("mike@doe.com", "mike", "Doe"));
		updatedPost.setImage("https://www.newsite.url/logo.png");

		ResultActions resultActions =
				mockMvc.perform(put("/post/"+0)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(updatedPost)));

		resultActions
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.success").value(false));

	}

	// ASSIGN CATEGORY TEST
	@Test
	void createNewPost_assignCategory() throws Exception {
		int id = createNewPost().getId();
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setName("Sport");

		ResultActions resultActions =
				mockMvc.perform(post("/post/"+id+"/assignCategory")
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(categoryDTO)));

		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.payload.category").isNotEmpty());
	}

	// ASSIGN TAGS TEST
	@Test
	void createNewPost_assignTags() throws Exception {
		int id = createNewPost().getId();
		List<String> tags = Arrays.asList("SPORT","ART","CULTURE");

		ResultActions resultActions =
				mockMvc.perform(post("/post/"+id+"/assignTags")
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(tags)));

		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.payload.tags").isNotEmpty());
	}

	//REMOVE TAGS TEST
	@Test
	void createNewPostAndAddTags_removeTags() throws Exception {
		int id = createNewPost().getId();
		List<String> tags = Arrays.asList("SPORT","ART","CULTURE");

		mockMvc.perform(post("/post/"+id+"/assignTags")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(tags)));

		ResultActions resultActions =
				mockMvc.perform(post("/post/"+id+"/removeTags")
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(tags)));

		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.payload.tags").isEmpty());

	}

	private PostDTO createNewPost() throws Exception {
		PostDTO postDTO = new PostDTO();
		postDTO.setTitle("New Title");
		postDTO.setContent("New Content");
		postDTO.setAuthor(new AuthorDTO("john@doe.com", "John", "Doe"));
		postDTO.setImage("https://www.site.url/logo.png");

		MvcResult result = mockMvc.perform(post("/post/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(postDTO))

		).andReturn();

		Response response = mapper.readValue(result.getResponse().getContentAsString(), Response.class);
		return mapper.convertValue(response.getPayload(), PostDTO.class);
	}
}
