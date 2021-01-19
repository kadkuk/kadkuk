package ee.bcs.valiit.tasks.controller;

import ee.bcs.valiit.tasks.Lesson1MathUtil;
import ee.bcs.valiit.tasks.Lesson2;
import ee.bcs.valiit.tasks.Lesson3;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerLesson3 {

    @GetMapping("sum")
    public int sum(@RequestParam("values") int [] values) {
        return Lesson3.sum(values);
    }

    @GetMapping("fac/{a}")
    public int fac(@PathVariable("a") int aVariable) {
        return Lesson3.factorial(aVariable);
    }

    @GetMapping("sort")
    public int [] sort(@RequestParam("values") int [] values) {
        return Lesson3.sort(values);
    }

    @GetMapping("reverseString")
    public String reverseString(@RequestParam("a") String aVariable) {
        return Lesson3.reverseString(aVariable);
    }

    @GetMapping("isPrime")
    public boolean isPrime(@RequestParam("a") int aVariable) {
        return Lesson3.isPrime(aVariable);
    }
}
