package ee.bcs.valiit.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Lesson3HardTest {

    @Test
    void evenFibonacci() {
        assertEquals(44, Lesson3Hard.evenFibonacci(10));
        assertEquals(0, Lesson3Hard.evenFibonacci(1));
    }

    @Test
    void morseCode() {
        assertEquals(". -   . - - .   . - - .   . .   ", Lesson3Hard.morseCode("appi"));
    }
}