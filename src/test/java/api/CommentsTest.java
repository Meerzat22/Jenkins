package api;


import org.assertj.core.api.Assertions;
import org.example.gorest.ConfigurationManager;
import org.example.gorest.contollers.PostController;
import org.example.gorest.contollers.UserController;
import org.example.gorest.models.Comment;
import org.example.gorest.models.Post;
import org.example.gorest.models.RandomUtils;
import org.example.gorest.models.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
@Tag("API")
@Tag("REGRESSION")
public class CommentsTest {

    PostController postController =
            new PostController(ConfigurationManager.getBaseConfig().gorestBaseUrl());
    UserController userController =
            new UserController(ConfigurationManager.getBaseConfig().gorestBaseUrl());

    Integer postId;

    @BeforeEach
    void setUp() {
        Users user = userController.createNewUser(RandomUtils.generateUsers());
        Post post = postController.createUserPost(user.getId(), Post.builder()
                .title("Test post")
                .body("Test body")
                .build());
        postId = post.getId();
    }

    @Test
    @Tag("SMOKE")
    void createCommentTest() {
        Comment comment = Comment.builder()
                .name("Sanjar")
                .email("test" + System.currentTimeMillis() + "@test.com")
                .body("Hello bro")
                .build();

        Comment actual = postController.createPostComment(postId, comment);

        Assertions.assertThat(postController.getResponse().getStatusCode()).isEqualTo(201);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id", "post_id")
                .isEqualTo(comment);

        Assertions.assertThat(actual.getPost_id()).isEqualTo(postId);
        Assertions.assertThat(actual.getId()).isNotNull();
    }

    @Test
    void negativeCreateCommentNonExistentPost() {
        Comment comment = Comment.builder()
                .name("Sanjar")
                .email("test" + System.currentTimeMillis() + "@test.com")
                .body("Hello bro")
                .build();

        postController.createPostComment(999999999, comment);

        Assertions.assertThat(postController.getResponse().getStatusCode()).isEqualTo(422);
    }

    @Test
    void negativeCreateCommentEmptyName() {
        Comment comment = Comment.builder()
                .name("")
                .email("test" + System.currentTimeMillis() + "@test.com")
                .body("Hello bro")
                .build();

        postController.createPostComment(postId, comment);

        Assertions.assertThat(postController.getResponse().getStatusCode()).isEqualTo(422);
        Assertions.assertThat(postController.getResponse().jsonPath().getString("[0].field"))
                .as("Field should be 'name' when name is empty").isEqualTo("name");
        Assertions.assertThat(postController.getResponse().jsonPath().getString("[0].message"))
                .as("Message should be 'can't be blank' when name is empty").isEqualTo("can't be blank");
    }

    @Test
    void negativeCreateCommentEmptyBody() {
        Comment comment = Comment.builder()
                .name("Sanjar")
                .email("test" + System.currentTimeMillis() + "@test.com")
                .body("")
                .build();

        postController.createPostComment(postId, comment);

        Assertions.assertThat(postController.getResponse().getStatusCode()).isEqualTo(422);
        Assertions.assertThat(postController.getResponse().jsonPath().getString("[0].field"))
                .as("Field should be 'body' when body is empty").isEqualTo("body");
        Assertions.assertThat(postController.getResponse().jsonPath().getString("[0].message"))
                .as("Message should be 'can't be blank' when body is empty").isEqualTo("can't be blank");
    }

    @Test
    void negativeCreateCommentInvalidEmail() {
        Comment comment = Comment.builder()
                .name("Sanjar")
                .email("notanemail")
                .body("Hello bro")
                .build();

        postController.createPostComment(postId, comment);

        Assertions.assertThat(postController.getResponse().getStatusCode()).isEqualTo(422);
        Assertions.assertThat(postController.getResponse().jsonPath().getString("[0].field"))
                .as("Field should be 'email' when email is invalid").isEqualTo("email");
        Assertions.assertThat(postController.getResponse().jsonPath().getString("[0].message"))
                .as("Message should be 'is invalid' when email is invalid").isEqualTo("is invalid");
    }
}