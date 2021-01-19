package ee.bcs.valiit.tasks.controller;

import ee.bcs.valiit.tasks.Lesson3;
import ee.bcs.valiit.tasks.Lesson3Hard;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerLesson3Hard {
    static int randomNumber = (int) (Math.random() * 100) + 1;
    static String answer = "";
    static int count = 0;

    @GetMapping("evenFibonacci/{a}")
    public int evenFibonacci(@PathVariable("a") int aVariable){

        return Lesson3Hard.evenFibonacci(aVariable);
    }

    @GetMapping("randomgame")
    public String randomgame(@RequestParam("a") int aVariable) {
        count++;
        if (count > 10) {
            answer = "Sinu katsed on otsas! Õige vastus on "+randomNumber + ". Arva ära uus number." ;
            count = 0;
            randomNumber = (int) (Math.random() * 100) + 1;
            return answer;
        } else if (aVariable == randomNumber) {
            answer = "Õige! Arvasid ära "+ count + ". korral. Arva ära uus number." ;
            count = 0;
            randomNumber = (int) (Math.random() * 100) + 1;
            return answer;
        } else {
            return Lesson3Hard.randomGame(aVariable, randomNumber);
        }
    }

    @GetMapping("morse")
    public String morse(@RequestParam("value") String valueVariable) {
        return Lesson3Hard.morseCode(valueVariable);
    }
}
