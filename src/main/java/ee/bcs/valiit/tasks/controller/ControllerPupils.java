package ee.bcs.valiit.tasks.controller;

import ee.bcs.valiit.tasks.Pupils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
public class ControllerPupils {

    HashMap<Integer, Pupils> pupilsMap = new HashMap<>();
    static int id = 1;


    @GetMapping("pupils/{name}/{school}/{city}/{age}")
    public Pupils pupils(@PathVariable("name") String nameVariable, @PathVariable("school") String schoolVariable, @PathVariable("city") String cityVariable, @PathVariable("age") int ageVariable) {
        Pupils pupil = new Pupils();
        pupil.setName(nameVariable);
        pupil.setSchoolName(schoolVariable);
        pupil.setCity(cityVariable);
        pupil.setAge(ageVariable);
        return pupil;
    }

    @PostMapping("pupils")
    public Pupils addPupils(@RequestBody Pupils pupil) {
        pupilsMap.put(id, pupil);
        id++;
        return pupil;
    }

    @GetMapping("pupils/{all}")
    public HashMap<Integer, Pupils> seePupils(@PathVariable("all") String allVariable) {
        return pupilsMap;
    }

    @GetMapping("pupil/{id}")
    public Pupils seeOne(@PathVariable("id") int idVariable) {
        return pupilsMap.get(idVariable);
    }

    @DeleteMapping("pupil/{id}")
    public HashMap<Integer, Pupils> deleteOne(@PathVariable("id") int idVariable) {
        pupilsMap.remove(idVariable);
        id--;
        return pupilsMap;

    }

    @PutMapping("pupil/{id}")
    public HashMap<Integer, Pupils> replaceOne(@PathVariable("id") int idVariable, @RequestBody Pupils pupil) {
        pupilsMap.put(idVariable, pupil);
        return pupilsMap;
    }
}



