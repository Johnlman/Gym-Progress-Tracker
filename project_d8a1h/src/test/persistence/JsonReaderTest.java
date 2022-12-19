package persistence;

import model.Exercise;
import model.GymSession;
import model.Set;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            User user = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyUser() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyUser.json");
        try {
            User user = reader.read();
            assertEquals("John", user.getName());
            assertEquals(0, user.getNumberGymSessions());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralUser() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralUser.json");
        try {
            User user = reader.read();
            String Bench = user.getGymSessions().get(0).getExercise("Bench").getName();
            String Squat = user.getGymSessions().get(0).getExercise("Squat").getName();
            assertEquals("John", user.getName());
            assertEquals(1, user.getNumberGymSessions());
            assertEquals("0/0/0", user.getGymSessions().get(0).getDate());
            assertEquals(Bench, "Bench");
            assertEquals(Squat,"Squat");
            assertEquals(user.getGymSessions().get(0).getExerciseList().size(), 2);
            assertEquals(user.getGymSessions().get(0).getExercise("Bench").getSets().get(0).getWeight(), 100);
            assertEquals(user.getGymSessions().get(0).getExercise("Bench").getSets().get(0).getReps(), 10);
            assertEquals(user.getGymSessions().get(0).getExercise("Squat").getSets().get(0).getWeight(), 100);
            assertEquals(user.getGymSessions().get(0).getExercise("Squat").getSets().get(0).getReps(), 10);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}