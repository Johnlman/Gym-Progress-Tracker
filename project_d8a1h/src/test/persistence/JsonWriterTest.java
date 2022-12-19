package persistence;

import model.Exercise;
import model.GymSession;
import model.Set;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            User user = new User("John");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyUser() {
        try {
            User user = new User("John");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyUser.json");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyUser.json");
            user = reader.read();
            assertEquals("John", user.getName());
            assertEquals(0, user.getNumberGymSessions());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralUser() {
        try {
            User user = new User("John");
            GymSession gymSession = new GymSession(0,0,0);
            Exercise bench = new Exercise("Bench",user);
            Set benchSet1 = new Set(10, 100,user,bench);
            bench.addSet(benchSet1);
            gymSession.addExercise(bench);
            user.getGymSessions().add(gymSession);
            Exercise squat = new Exercise("Squat",user);
            Set squatSet1 = new Set(10, 100,user,squat);
            squat.addSet(squatSet1);
            gymSession.addExercise(squat);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralUser.json");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralUser.json");
            user = reader.read();
            assertEquals("John", user.getName());
            assertEquals(1, user.getNumberGymSessions());
            assertEquals("0/0/0", gymSession.getDate());
            assertEquals(gymSession.getExercise("Bench"), bench);
            assertEquals(gymSession.getExercise("Squat"), squat);
            assertEquals(gymSession.getExerciseList().size(), 2);
            assertEquals(gymSession.getExercise("Bench").getSets().get(0), benchSet1);
            assertEquals(gymSession.getExercise("Bench").getSets().get(0).getWeight(), 100);
            assertEquals(gymSession.getExercise("Bench").getSets().get(0).getReps(), 10);
            assertEquals(gymSession.getExercise("Squat").getSets().get(0), squatSet1);
            assertEquals(gymSession.getExercise("Squat").getSets().get(0).getWeight(), 100);
            assertEquals(gymSession.getExercise("Squat").getSets().get(0).getReps(), 10);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}