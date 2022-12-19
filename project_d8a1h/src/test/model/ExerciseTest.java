package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExerciseTest {
    Exercise exercise;
    User user;
    @BeforeEach
    public void starter() {
        exercise = new Exercise("Bench",user);
    }
    @Test
    public void constructorTest() {
        assertEquals(exercise.getName(), "Bench");

        assertEquals(exercise.getSets().size(),0);
    }
    @Test
    public void addSetTest() {
        Set set = new Set(10,10,user,exercise);
        exercise.addSet(set);
        assertEquals(exercise.getSets().get(0), set);
    }
    @Test
    public void addMultipleSetsTest() {
        Set set = new Set(10,10,user,exercise);
        Set set1 = new Set(5,10,user,exercise);
        exercise.addSet(set);
        exercise.addSet(set1);
        assertEquals(exercise.getSets().get(1), set1);
        assertEquals(exercise.getSets().get(0), set);
    }
    @Test
    public void getMaxWeightTest() {
        Set set1 = new Set(2,4,user,exercise);
        Set set2 = new Set(4,3,user,exercise);
        exercise.addSet(set1);
        exercise.addSet(set2);
        assertEquals(exercise.getMaxWeight(),4);
    }
    @Test
    public void getTotalRepsTest() {
        Set set1 = new Set(10,5,user,exercise);
        Set set2 = new Set(2,20,user,exercise);
        exercise.addSet(set1);
        exercise.addSet(set2);
        assertEquals(exercise.getTotalReps(),12);
    }
    /*@Test
    public void getCardioDurationTest() {
        exercise.setCardioDuration(60);
        assertEquals(exercise.getCardioDuration(),60);
    }*/
}
