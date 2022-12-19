package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SetTest {
    Set set;
    Exercise exercise;
    User user;
    @BeforeEach
    public void starter() {
        user = new User("John");
        exercise = new Exercise("Bench",user);
        set = new Set(10,10,user,exercise);
    }
    @Test
    public void constructorTest() {
        assertEquals(set.getReps(),10);
        assertEquals(set.getWeight(), 10);
    }

}
