package api;

import org.assertj.core.api.Assertions;
import org.example.gorest.ConfigurationManager;
import org.example.gorest.contollers.TodoController;
import org.example.gorest.contollers.UserController;
import org.example.gorest.models.RandomUtils;
import org.example.gorest.models.Todo;
import org.example.gorest.models.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
@Tag("API")
@Tag("REGRESSION")

public class TodosTest {

    TodoController todoController =
            new TodoController(ConfigurationManager.getBaseConfig().gorestBaseUrl());
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
    void createTodoTest() {
        Users random = RandomUtils.generateUsers();
        Users user = userController.createNewUser(random);
        Todo todo = Todo.builder()
                .user_id(user.getId())
                .title("Finish testing")
                .status("pending")
                .due_on("2026-03-30T12:00:00.000+05:00")
                .build();

        Todo actual = todoController.createUserTodo(userId, todo);

        Assertions.assertThat(todoController.getResponse().getStatusCode()).isEqualTo(201);
        Assertions.assertThat(actual.getId()).as("actual id is null").isNotEqualTo(null);
    }

    @Test
    void negativeCreateTodoInvalidStatus() {
        Todo todo = Todo.builder()
                .user_id(userId)
                .title("Finish testing")
                .status("wrong_status")
                .build();

        todoController.createUserTodo(userId, todo);

        Assertions.assertThat(todoController.getResponse().getStatusCode()).isEqualTo(422);
        Assertions.assertThat(todoController.getResponse().jsonPath().getString("[0].field"))
                .as("Field should be 'status' when status is invalid").isEqualTo("status");
        Assertions.assertThat(todoController.getResponse().jsonPath().getString("[0].message"))
                .as("Message should indicate valid values when status is 'wrong_status'").isEqualTo("can't be blank, can be pending or completed");
    }

    @Test
    void negativeCreateTodoEmptyTitle() {
        Todo todo = Todo.builder()
                .user_id(userId)
                .title("")
                .status("pending")
                .build();

        todoController.createUserTodo(userId, todo);

        Assertions.assertThat(todoController.getResponse().getStatusCode()).isEqualTo(422);
        Assertions.assertThat(todoController.getResponse().jsonPath().getString("[0].field"))
                .as("Field should be 'title' when title is empty").isEqualTo("title");
        Assertions.assertThat(todoController.getResponse().jsonPath().getString("[0].message"))
                .as("Message should be 'can't be blank' when title is empty").isEqualTo("can't be blank");
    }

    @Test
    void negativeCreateTodoNonExistentUser() {
        Todo todo = Todo.builder()
                .user_id(999999999)
                .title("Finish testing")
                .status("pending")
                .build();

        todoController.createUserTodo(999999999, todo);

        Assertions.assertThat(todoController.getResponse().getStatusCode()).isEqualTo(422);
        Assertions.assertThat(todoController.getResponse().jsonPath().getString("[0].field"))
                .as("Field should be 'user' when user does not exist").isEqualTo("user");
        Assertions.assertThat(todoController.getResponse().jsonPath().getString("[0].message"))
                .as("Message should be 'must exist' when user does not exist").isEqualTo("must exist");
    }
}