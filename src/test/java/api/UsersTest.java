package api;


import org.assertj.core.api.Assertions;
import org.example.gorest.ConfigurationManager;
import org.example.gorest.CsvUtils;
import org.example.gorest.contollers.UserController;
import org.example.gorest.models.RandomUtils;
import org.example.gorest.models.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("API")
@Tag("REGRESSION")
public class UsersTest {

    private static final String CSV_PATH = "src/test/resources/users.csv";
    UserController userController = new UserController(ConfigurationManager.getBaseConfig().gorestBaseUrl());

    Integer userId;

    @BeforeEach
    void setUp() {
        Users user = userController.createNewUser(RandomUtils.generateUsers());
        userId = user.getId();
    }

    @Test
    public void userTest() {
        Users[] users = userController.getAllUsers();

    }

    @Test
    void saveUsersToCsv() {
        Users[] all = userController.getAllUsers();

        CsvUtils.writeUsersToCsv(Arrays.asList(all), CSV_PATH);

        Assertions.assertThat(Paths.get(CSV_PATH)).exists();
    }

    @Test
    void readUsersFromCsv() {
        Users[] all = userController.getAllUsers();
        CsvUtils.writeUsersToCsv(Arrays.asList(all), CSV_PATH);

        List<Users> users = CsvUtils.readUsersFromCsv(CSV_PATH);

        Assertions.assertThat(users).isNotEmpty();
        users.forEach(System.out::println);
    }

    Integer idUser;
    @Test
    @Tag("SMOKE")
    void createUserRandom(){
        Users random = RandomUtils.generateUsers();
        Users actualUser1 = userController.createNewUser(random);

        assertEquals(random.getName(),actualUser1.getName());
        assertEquals(random.getEmail(),actualUser1.getEmail());
        assertEquals(random.getGender().toLowerCase(),actualUser1.getGender().toLowerCase());
        assertEquals(random.getStatus(),actualUser1.getStatus());

        Assertions.assertThat(userController.getResponse().getStatusCode()).as("Actual and expected" +
                "status core are mismatch").isEqualTo(201);

        Assertions.assertThat(actualUser1).as("Responce body mismatch")
                .usingRecursiveComparison()
                .ignoringFields("id")
                .withComparatorForType(String.CASE_INSENSITIVE_ORDER, String.class)
                .isEqualTo(random);

        Assertions.assertThat(userController.getResponse().getTime()).
                as("More than 1m").isLessThan(10000);

        Assertions.assertThat(userController.getResponse().getHeader("x-frame-options"))
                .as("is not SAMEORIGIN")
                .isEqualTo("SAMEORIGIN");

        Assertions.assertThat(userController.getResponse().asByteArray().length)
                .as("Error")
                .isGreaterThan(0);

        Assertions.assertThat(actualUser1.getId()).as("id null").isNotEqualTo(0);

        //userController.patchUser(actualUser1.getId(),);
    }

    @Test
    @Tag("SMOKE")
    void getUser(){
        Users user = userController.getUser(userId);
    }

    @Test
    @Tag("SMOKE")
    void updateUserTest() {
        Users random = RandomUtils.generateUsers();
        Users created = userController.createNewUser(random);

        Integer id = created.getId();

        Users updatedData = Users.builder()
                .name("koko Shnene")
                .email("vatafa" + System.currentTimeMillis() + "@example.com")
                .gender("female")
                .status("active")
                .build();

        Users updatedUser = userController.updateUser(id, updatedData);

        Assertions.assertThat(userController.getResponse().getStatusCode()).as("Actual and expected" +
                "code is mismatch").isEqualTo(200);
    }

    @Test
    @Tag("SMOKE")
    void delete(){
        int statusCode = userController.deleteUser(userId);
        assertEquals(204, statusCode);
    }

    @Test
    void createUserWithEmptyName() {
        Users user = Users.builder()
                .name("")
                .email(RandomUtils.generateUsers().getEmail())
                .gender("male")
                .status("active")
                .build();

        userController.createNewUser(user);

        Assertions.assertThat(userController.getResponse().getStatusCode()).isEqualTo(422);
        Assertions.assertThat(userController.getResponse().jsonPath().getString("[0].field"))
                .as("Should contain field name").isEqualTo("name");
        Assertions.assertThat(userController.getResponse().jsonPath().getString("[0].message"))
                .as("Should contain message can't be blank").isEqualTo("can't be blank");
    }

    @Test
    void createUserWithEmptyEmail() {
        Users user = Users.builder()
                .name("Test User")
                .email("")
                .gender("female")
                .status("active")
                .build();

        userController.createNewUser(user);

        Assertions.assertThat(userController.getResponse().getStatusCode()).isEqualTo(422);
        Assertions.assertThat(userController.getResponse().asString()).contains("email");
    }

    @Test
    void createUserWithInvalidEmail() {
        Users user = Users.builder()
                .name("Test User")
                .email("notanemail")
                .gender("male")
                .status("active")
                .build();

        userController.createNewUser(user);

        Assertions.assertThat(userController.getResponse().getStatusCode()).isEqualTo(422);
        Assertions.assertThat(userController.getResponse().jsonPath().getString("[0].field"))
                .as("Should contain field email")
                .isEqualTo("email");

        Assertions.assertThat(userController.getResponse().jsonPath().getString("[0].message"))
                .as("Should contain message is invalid")
                .isEqualTo("is invalid");
    }

    @Test
    void createUserWithInvalidGender() {
        Users user = Users.builder()
                .name("Test User")
                .email("valid" + System.currentTimeMillis() + "@test.com")
                .gender("attack_helicopter")
                .status("active")
                .build();

        userController.createNewUser(user);

        Assertions.assertThat(userController.getResponse().getStatusCode()).isEqualTo(422);
        Assertions.assertThat(userController.getResponse().jsonPath().getString("[0].field"))
                .as("Should contain message gender").isEqualTo("gender");
        Assertions.assertThat(userController.getResponse().jsonPath().getString("[0].message"))
                .as("Request with invalid gender")
                .isEqualTo("can't be blank, can be male of female");
    }

    @Test
    void createUserWithInvalidStatus() {
        Users user = Users.builder()
                .name("Test User")
                .email("valid" + System.currentTimeMillis() + "@test.com")
                .gender("male")
                .status("superactive")
                .build();

        userController.createNewUser(user);

        Assertions.assertThat(userController.getResponse().getStatusCode()).isEqualTo(422);
        Assertions.assertThat(userController.getResponse().jsonPath().getString("[0].field"))
                .as("Should contain message status").isEqualTo("status");
        Assertions.assertThat(userController.getResponse().jsonPath().getString("[0].message"))
                .as("Request with invalid status")
                .isEqualTo("can't be blank");
    }

    @Test
    void createUserWithDuplicateEmail() {
        Users random = RandomUtils.generateUsers();
        userController.createNewUser(random);

        userController.createNewUser(random);

        Assertions.assertThat(userController.getResponse().getStatusCode()).isEqualTo(422);
        Assertions.assertThat(userController.getResponse().jsonPath().getString("[0].field"))
                .as("Should contain message email").isEqualTo("email");
        Assertions.assertThat(userController.getResponse().jsonPath().getString("[0].message"))
                .as("Request with same email").isEqualTo("has already been taken");
    }

    @Test
    void getUserNotFound() {
        userController.getUser(999999999);

        Assertions.assertThat(userController.getResponse().getStatusCode()).isEqualTo(404);
    }

    @Test
    void deleteNonExistentUser() {
        int statusCode = userController.deleteUser(999999999);

        assertEquals(404, statusCode);
    }

    @Test
    void updateUserWithEmptyName() {
        Users random = RandomUtils.generateUsers();
        Users created = userController.createNewUser(random);

        Integer id = created.getId();

        Users invalidUpdate = Users.builder()
                .name("")
                .email("valid" + System.currentTimeMillis() + "@test.com")
                .gender("male")
                .status("active")
                .build();

        userController.updateUser(id, invalidUpdate);

        Assertions.assertThat(userController.getResponse().getStatusCode()).isEqualTo(422);
        Assertions.assertThat(userController.getResponse().asString()).contains("name");
    }
}