package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GymSessionTest {
    GymSession gymSession;
    User user;
    @BeforeEach
    public void starter() {
        gymSession = new GymSession(2022,10,11);
    }
    @Test
    public void constructorTest() {
        assertEquals(gymSession.getExerciseList().size(), 0);
    }
    @Test
    public void getDateTest() {
        assertEquals(gymSession.getDate(), new Date(2022,10,11));
    }
    @Test
    public void addExerciseTest() {
        Exercise bench = new Exercise("bench",user);
        gymSession.addExercise(bench);
        assertEquals(bench,gymSession.getExercise("bench"));
    }
    @Test
    public void addExercisesSameNameTest() {
        Exercise bench = new Exercise("bench",user);
        Exercise bench2 = new Exercise("bench",user);
        gymSession.addExercise(bench);
        gymSession.addExercise(bench2);
        assertEquals(bench2,gymSession.getExercise("bench"));
    }
    @Test
    public void addMultipleExercisesTest() {
        Exercise bench = new Exercise("bench",user);
        Exercise squat = new Exercise("Squat",user);
        gymSession.addExercise(bench);
        gymSession.addExercise(squat);
        assertEquals(bench,gymSession.getExercise("bench"));
        assertEquals(squat,gymSession.getExercise("squat"));
    }
}
