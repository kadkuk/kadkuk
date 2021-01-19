package ee.bcs.valiit.tasks.controller;

import ee.bcs.valiit.tasks.Lesson1MathUtil;
import ee.bcs.valiit.tasks.Lesson2;
import ee.bcs.valiit.tasks.Lesson3;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ControllerLesson2 {

    @GetMapping("array")
    public int [] array(@RequestParam("values") int [] values) {
        return Lesson2.exercise1(values);
    }

    @GetMapping("equals/{x}")
    public List<Integer> equals(@PathVariable("x") int xVariable) {
        return Lesson2.exercise2(xVariable);
    }

        @GetMapping("fibonacci")
        public int fibonacci ( @RequestParam("a") int aVariable){
            return Lesson2.exercise4(aVariable);
        }

        @GetMapping("maxLenght")
        public String maxLength ( @RequestParam("a") int aVariable, @RequestParam("b") int bVariable){
            return Lesson2.exercise5(aVariable, bVariable);
        }

        @GetMapping("seqLenght")
        public int seqLength ( @RequestParam("a") int aVariable){
            return Lesson2.seqLength(aVariable);
        }
    }

