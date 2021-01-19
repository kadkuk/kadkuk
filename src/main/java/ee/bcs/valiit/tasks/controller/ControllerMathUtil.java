package ee.bcs.valiit.tasks.controller;

import ee.bcs.valiit.tasks.Lesson1MathUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerMathUtil {

    @GetMapping("min/{a}/{b}")
        public int min(@PathVariable("a") int aVariable, @PathVariable("b") int bVariable) {
        return Lesson1MathUtil.min(aVariable, bVariable);
    }

    @GetMapping("max/{a}/{b}")
    public int max(@PathVariable("a") int aVariable, @PathVariable("b") int bVariable) {
        return Lesson1MathUtil.max(aVariable, bVariable);
    }

    @GetMapping("abs")
    public int abs(@RequestParam("a") int aVariable) {
        return Lesson1MathUtil.abs(aVariable);
    }

    @GetMapping("isEven")
    public boolean isEven(@RequestParam("a") int aVariable) {
        return Lesson1MathUtil.isEven(aVariable);
    }

    @GetMapping("min/{a}/{b}/{c}")
    public int min(@PathVariable("a") int aVariable, @PathVariable("b") int bVariable, @PathVariable("c") int cVariable) {
        return Lesson1MathUtil.min(aVariable, bVariable, cVariable);
    }

    @GetMapping("max/{a}/{b}/{c}")
    public int max(@PathVariable("a") int aVariable, @PathVariable("b") int bVariable, @PathVariable("c") int cVariable) {
        return Lesson1MathUtil.max(aVariable, bVariable, cVariable);
    }
}


