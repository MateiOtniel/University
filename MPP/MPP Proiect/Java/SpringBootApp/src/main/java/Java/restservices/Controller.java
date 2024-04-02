package Java.restservices;

import model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import persistence.repository.dbrepositories.TestRepository;

import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping({"/java/tests"})
public class Controller {

    @Autowired
    private TestRepository testRepository;

    @CrossOrigin
    @GetMapping
    public ArrayList<Test> getAll() {
        System.out.println("Get all tests ...");
        return testRepository.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        System.out.println("Get by id " + id);
        Test test = testRepository.getById(id);
        if (test == null)
            return new ResponseEntity<>("Test not found",
                    HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(test, HttpStatus.OK);
    }

    @PostMapping
    public Test saveTest(@RequestBody Test test){
        System.out.println(test);
        testRepository.add(test);
        return test;
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTest(@PathVariable int id, @RequestBody Test test){
        testRepository.update(id, test);
        return new ResponseEntity<>(test, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTest(@PathVariable int id){
        testRepository.removeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
