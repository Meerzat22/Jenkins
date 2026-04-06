package db;


import org.assertj.core.api.Assertions;
import org.example.db.beans.Actor;
import org.example.db.db_utils.DB_Connection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
@Tag("DB")
@Tag("REGRESSION")
public class ActorTest {

    @BeforeAll
    static void setUp() throws SQLException {
        DB_Connection.openConnection("dvdRental");
    }

    @AfterAll
    static void tearDown() {
        DB_Connection.closeConnection();
    }

    @Test
    void actorTest() throws SQLException {
        DB_Connection.openConnection("dvdRental");
        Actor actor = Actor.getBy("actor_id", 1);
        System.out.println(actor);
    }

    @Test
    void getActorByIdTest() throws SQLException {
        Actor actor = Actor.getBy("actor_id", 1);

        Assertions.assertThat(actor)
                .as("Actor with id=1 should exist")
                .isNotNull();

        Assertions.assertThat(actor.getActor_id())
                .as("Actor id should be 1")
                .isEqualTo(1);

        System.out.println("Found actor: " + actor);
    }

    @Test
    void insertActorTest() throws SQLException {
        Actor.insert("John", "Doe");

        List<Actor> all = Actor.getAllActors();

        Assertions.assertThat(all)
                .as("Inserted actor John Doe should exist in DB")
                .anySatisfy(actor -> {
                    Assertions.assertThat(actor.getFirst_name()).isEqualTo("John");
                    Assertions.assertThat(actor.getLast_name()).isEqualTo("Doe");
                });

        System.out.println("Inserted actor: John Doe");
    }

    @Test
    void updateActorTest() throws SQLException {

        Actor actor = Actor.getBy("actor_id", 1);
        Integer id = actor.getActor_id();

        Actor.update(id, "UpdatedName", "UpdatedLastName");

        Actor updated = Actor.getBy("actor_id", id);

        Assertions.assertThat(updated.getFirst_name())
                .as("First name should be updated")
                .isEqualTo("UpdatedName");

        Assertions.assertThat(updated.getLast_name())
                .as("Last name should be updated")
                .isEqualTo("UpdatedLastName");

        System.out.println("Updated actor: " + updated);
    }

    @Test
    void deleteActorTest() throws SQLException {

        Actor.insert("ToDelete", "Actor");

        List<Actor> before = Actor.getAllActors();
        Actor toDelete = before.stream()
                .filter(a -> a.getFirst_name().equals("ToDelete"))
                .findFirst()
                .orElseThrow();

        Actor.delete(toDelete.getActor_id());

        Actor deleted = Actor.getBy("actor_id", toDelete.getActor_id());

        Assertions.assertThat(deleted)
                .as("Actor should be deleted from DB")
                .isNull();

        System.out.println("Deleted actor id: " + toDelete.getActor_id());
    }

    @Test
    void getActorNotFoundTest() throws SQLException {
        Actor actor = Actor.getBy("actor_id", 999999);

        Assertions.assertThat(actor)
                .as("Actor with id=999999 should not exist")
                .isNull();
    }
}