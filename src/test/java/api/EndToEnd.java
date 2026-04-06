package api;


import org.assertj.core.api.Assertions;
import org.example.gorest.ConfigurationManager;
import org.example.gorest.contollers.PostController;
import org.example.gorest.contollers.TodoController;
import org.example.gorest.contollers.UserController;
import org.example.gorest.models.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
@Tag("API")
@Tag("E2E")
@Tag("REGRESSION")
public class EndToEnd {

    static UserController userController;


    @BeforeAll
    static void initControllers(){
        userController = new UserController(ConfigurationManager.getBaseConfig().gorestBaseUrl());
    }

        PostController postController =
                new PostController(ConfigurationManager.getBaseConfig().gorestBaseUrl());
        TodoController todoController =
                new TodoController(ConfigurationManager.getBaseConfig().gorestBaseUrl());

        @Test
        @DisplayName("E2E: create user -> create post -> create comment -> create todo -> delete user")
        void shouldPerformFullUserWorkflowFromCreationToDeletion() {

            // 1. Create user
            Users user = RandomUtils.generateUsers();
            Users createdUser = userController.createNewUser(user);
            Integer userId = createdUser.getId();

            Assertions.assertThat(userController.getResponse().getStatusCode())
                    .as("User should be created with status 201")
                    .isEqualTo(201);
            Assertions.assertThat(userId)
                    .as("Created user id should not be null")
                    .isNotNull();

            // 2. Create post
            Post post = Post.builder()
                    .user_id(userId)
                    .title("E2E Test Post")
                    .body("E2E Test Body")
                    .build();
            Post createdPost = postController.createUserPost(userId, post);
            Integer postId = createdPost.getId();

            Assertions.assertThat(postController.getResponse().getStatusCode())
                    .as("Post should be created with status 201")
                    .isEqualTo(201);
            Assertions.assertThat(postId)
                    .as("Created post id should not be null")
                    .isNotNull();

            // 3. Create comment
            Comment comment = Comment.builder()
                    .post_id(postId)
                    .name("E2E Commenter")
                    .email("e2e" + System.currentTimeMillis() + "@test.com")
                    .body("E2E Comment Body")
                    .build();
            Comment createdComment = postController.createPostComment(postId, comment);
            Integer commentId = createdComment.getId();

            Assertions.assertThat(postController.getResponse().getStatusCode())
                    .as("Comment should be created with status 201")
                    .isEqualTo(201);
            Assertions.assertThat(commentId)
                    .as("Created comment id should not be null")
                    .isNotNull();

            // 4. Create todo
            Todo todo = Todo.builder()
                    .user_id(userId)
                    .title("E2E Test Todo")
                    .status("pending")
                    .due_on("2026-12-31T00:00:00.000+05:00")
                    .build();
            Todo createdTodo = todoController.createUserTodo(userId, todo);
            Integer todoId = createdTodo.getId();

            Assertions.assertThat(todoController.getResponse().getStatusCode())
                    .as("Todo should be created with status 201")
                    .isEqualTo(201);
            Assertions.assertThat(todoId)
                    .as("Created todo id should not be null")
                    .isNotNull();

            // 5. Delete user
            int deleteStatus = userController.deleteUser(userId);

            Assertions.assertThat(deleteStatus)
                    .as("User should be deleted with status 204")
                    .isEqualTo(204);
        }

    @Test
    @DisplayName("E2E Negative: create user with invalid data -> all subsequent steps should fail")
    void shouldFailWorkflowWithInvalidUser() {

        // 1. Create user with invalid data
        Users invalidUser = Users.builder()
                .name("")
                .email("notanemail")
                .gender("invalid")
                .status("invalid")
                .build();

        userController.createNewUser(invalidUser);

        Assertions.assertThat(userController.getResponse().getStatusCode())
                .as("Status code should be 422 when user data is invalid")
                .isEqualTo(422);

        // дальнейшие шаги невозможны — юзер не создан
    }

    @Test
    @DisplayName("E2E Negative: create post for non-existent user")
    void shouldFailCreatePostForNonExistentUser() {

        // 1. Create user
        Users user = RandomUtils.generateUsers();
        Users createdUser = userController.createNewUser(user);
        Integer userId = createdUser.getId();

        // 2. Delete user
        userController.deleteUser(userId);

        Assertions.assertThat(userController.getResponse().getStatusCode())
                .as("User should be deleted with status 204")
                .isEqualTo(204);

        // 3. Try to create post for deleted user
        Post post = Post.builder()
                .user_id(userId)
                .title("Post for deleted user")
                .body("Should fail")
                .build();

        postController.createUserPost(userId, post);

        Assertions.assertThat(postController.getResponse().getStatusCode())
                .as("Post creation should fail with 422 for deleted user")
                .isEqualTo(422);
        Assertions.assertThat(postController.getResponse().jsonPath().getString("[0].field"))
                .as("Field should be 'user' when user does not exist")
                .isEqualTo("user");
        Assertions.assertThat(postController.getResponse().jsonPath().getString("[0].message"))
                .as("Message should be 'must exist' when user does not exist")
                .isEqualTo("must exist");
    }

    @Test
    @DisplayName("E2E Negative: create comment for non-existent post")
    void shouldFailCreateCommentForNonExistentPost() {

        // 1. Create user
        Users user = RandomUtils.generateUsers();
        Users createdUser = userController.createNewUser(user);
        Integer userId = createdUser.getId();

        // 2. Create post
        Post post = Post.builder()
                .user_id(userId)
                .title("E2E Test Post")
                .body("E2E Test Body")
                .build();
        Post createdPost = postController.createUserPost(userId, post);
        Integer postId = createdPost.getId();

        // 3. Delete post via delete user (cascade)
        userController.deleteUser(userId);

        // 4. Try to create comment for deleted post
        Comment comment = Comment.builder()
                .post_id(postId)
                .name("Commenter")
                .email("test" + System.currentTimeMillis() + "@test.com")
                .body("Should fail")
                .build();

        postController.createPostComment(postId, comment);

        Assertions.assertThat(postController.getResponse().getStatusCode())
                .as("Comment creation should fail with 422 for deleted post")
                .isEqualTo(422);
        Assertions.assertThat(postController.getResponse().jsonPath().getString("[0].field"))
                .as("Field should be 'post' when post does not exist")
                .isEqualTo("post");
        Assertions.assertThat(postController.getResponse().jsonPath().getString("[0].message"))
                .as("Message should be 'must exist' when post does not exist")
                .isEqualTo("must exist");
    }

    @Test
    @DisplayName("E2E Negative: create todo with invalid status for existing user")
    void shouldFailCreateTodoWithInvalidStatus() {

        // 1. Create user
        Users user = RandomUtils.generateUsers();
        Users createdUser = userController.createNewUser(user);
        Integer userId = createdUser.getId();

        // 2. Create post
        Post post = Post.builder()
                .user_id(userId)
                .title("E2E Test Post")
                .body("E2E Test Body")
                .build();
        postController.createUserPost(userId, post);

        // 3. Try to create todo with invalid status
        Todo todo = Todo.builder()
                .user_id(userId)
                .title("Invalid Todo")
                .status("invalid_status")
                .build();

        todoController.createUserTodo(userId, todo);

        Assertions.assertThat(todoController.getResponse().getStatusCode())
                .as("Todo creation should fail with 422 when status is invalid")
                .isEqualTo(422);
        Assertions.assertThat(todoController.getResponse().jsonPath().getString("[0].field"))
                .as("Field should be 'status' when status is invalid")
                .isEqualTo("status");
        Assertions.assertThat(todoController.getResponse().jsonPath().getString("[0].message"))
                .as("Message should indicate valid values when status is invalid")
                .isEqualTo("can't be blank, can be pending or completed");

        // 4. Cleanup
        userController.deleteUser(userId);
    }
}
