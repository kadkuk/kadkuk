package ee.bcs.valiit.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Lesson3Test {

    @Test
    void sum() {
        assertEquals(21, Lesson3.sum(new int[]{1,2,3,4,5,6}));
    }

    @Test
    void factorial() {
        assertEquals(120, Lesson3.factorial(5));
    }

    @Test
    void sort() {
        assertArrayEquals(new int[]{2,3,4,5,6,7}, Lesson3.sort(new int[]{5,4,3,7,2,6}));
    }

    @Test
    void reverseString() {
        assertEquals("oollaH", Lesson3.reverseString("Halloo"));
    }

    @Test
    void isPrime() {
        assertTrue(Lesson3.isPrime(11));
        assertFalse(Lesson3.isPrime(8));
    }
}