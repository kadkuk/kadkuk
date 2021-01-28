package ee.bcs.valiit.tasks;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Lesson2Test {

    @Test
    void exercise1() {
        assertArrayEquals(new int []{2,3,4,5,6,7}, Lesson2.exercise1(new int[] {7,6,5,4,3,2}));
    }

    @Test
    void exercise2() {
        List<Integer> expectedResult = Arrays.asList(2,4,6,8,10);
        List<Integer> actualResult = Lesson2.exercise2(5);
        assertTrue(actualResult.equals(expectedResult));
    }

    @Test
    void exercise3() {
    }

    @Test
    void exercise4() {
        assertEquals(8, Lesson2.exercise4(6));
        assertEquals(1, Lesson2.exercise4(1));
    }

    @Test
    void exercise5() {
        assertEquals("2 1 0", Lesson2.exercise5(2, 1));
        assertEquals("3 4 8", Lesson2.exercise5(3, 4));
    }

    @Test
    void seqLength() {
        assertEquals(8, Lesson2.exercise4(6));
    }
}