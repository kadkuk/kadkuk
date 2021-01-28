package ee.bcs.valiit.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Lesson1MathUtilTest {

    @Test
    void min() {
        assertEquals(3, Lesson1MathUtil.min(3, 9));
        assertEquals(1, Lesson1MathUtil.min(4, 1));
    }

    @Test
    void max() {
        assertEquals(9, Lesson1MathUtil.max(3, 9));
        assertEquals(4, Lesson1MathUtil.max(4, 1));
    }

    @Test
    void abs() {
        assertEquals(1, Lesson1MathUtil.abs(-1));
        assertEquals(1, Lesson1MathUtil.abs(1));
    }

    @Test
    void isEven() {
        assertTrue(Lesson1MathUtil.isEven(4));
        assertFalse(Lesson1MathUtil.isEven(17));
    }

    @Test
    void testMin() {
        assertEquals(3, Lesson1MathUtil.min(3, 9, 3));
        assertEquals(1, Lesson1MathUtil.min(4, 1, 4));
        assertEquals(0, Lesson1MathUtil.min(4, 1, 0));
    }

    @Test
    void testMax() {
        assertEquals(9, Lesson1MathUtil.max(9, 9, 3));
        assertEquals(8, Lesson1MathUtil.max(4, 8, 4));
        assertEquals(11, Lesson1MathUtil.max(4, 4, 11));
    }
}