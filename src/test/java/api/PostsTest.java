package api;


import org.assertj.core.api.Assertions;
import org.example.gorest.ConfigurationManager;
import org.example.gorest.contollers.PostController;
import org.example.gorest.contollers.UserController;
import org.example.gorest.models.Post;
import org.example.gorest.models.RandomUtils;
import org.example.gorest.models.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
@Tag("API")
@Tag("REGRESSION")

public class PostsTest {

    PostController postController =
            new PostController(ConfigurationManager.getBaseConfig().gorestBaseUrl());
    UserController userController =
            new UserController(ConfigurationManager.getBaseConfig().gorestBaseUrl());

    Integer userId;

    @BeforeEach
    void setUp() {
        Users user = userController.createNewUser(RandomUtils.generateUsers());
        userId = user.getId();
    }

    @Test
    @Tag("SMOKE")
    void createPostTest() {
        Post post = Post.builder()
                .user_id(userId)
                .title("Test title")
                .body("Test body")
                .build();

        Post actual = postController.createUserPost(userId, post);

        Assertions.assertThat(postController.getResponse().getStatusCode()).isEqualTo(201);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(post);

        Assertions.assertThat(actual.getId()).isNotNull();
        Assertions.assertThat(postController.getResponse().getTime()).isLessThan(10000);
    }

    @Test
    @Tag("SMOKE")
    void getPostsByUserTest() {
        Post post = Post.builder()
                .user_id(userId)
                .title("Test title")
                .body("Test body")
                .build();
        postController.createUserPost(userId, post);

        Assertions.assertThat(postController.getPostsByUser(userId)).isNotEmpty();
    }

    @Test
    void negativeCreatePostEmptyTitle() {
        Post post = Post.builder()
                .user_id(userId)
                .title("")
                .body("Test body")
                .build();

        postController.createUserPost(userId, post);

        Assertions.assertThat(postController.getResponse().getStatusCode()).isEqualTo(422);
        Assertions.assertThat(postController.getResponse().jsonPath().getString("[0].field"))
                .as("Field should be 'title'").isEqualTo("title");
        Assertions.assertThat(postController.getResponse().jsonPath().getString("[0].message"))
                .as("Message should be can't be blank").isEqualTo("can't be blank");
    }

    @Test
    void negativeCreatePostEmptyBody() {
        Post post = Post.builder()
                .user_id(userId)
                .title("Test title")
                .body("")
                .build();

        postController.createUserPost(userId, post);

        Assertions.assertThat(postController.getResponse().getStatusCode()).isEqualTo(422);
        Assertions.assertThat(postController.getResponse().jsonPath().getString("[0].field"))
                .as("Field should be body").isEqualTo("body");
        Assertions.assertThat(postController.getResponse().jsonPath().getString("[0].message"))
                .as("Message should be 'can't be blank'").isEqualTo("can't be blank");
    }

    @Test
    void negativeCreatePostNonExistentUser() {
        Post post = Post.builder()
                .user_id(999999999)
                .title("Test title")
                .body("Test body")
                .build();

        postController.createUserPost(999999999, post);

        Assertions.assertThat(postController.getResponse().getStatusCode()).isEqualTo(422);
        Assertions.assertThat(postController.getResponse().jsonPath().getString("[0].field"))
                .as("Field should be user").isEqualTo("user");
        Assertions.assertThat(postController.getResponse().jsonPath().getString("[0].message"))
                .as("Message should be 'must exist'").isEqualTo("must exist");
    }
}