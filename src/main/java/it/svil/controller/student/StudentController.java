package it.svil.controller.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("")
    public Iterable<Student> getAllStudents(){
        return studentService.getStudents();
    }

    @GetMapping("/{id}")
    public Optional<Student> byId(@PathVariable Long id){
        return studentService.getById(id);
    }

    @PostMapping()
    public Student add(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

//    @DeleteMapping("/{id}")
//    public List<Student> deleteById(@PathVariable Long id) {
//        return studentService.delete(id);
//    }

    @PutMapping("/{id}")
    public Student putStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.putStudent(id, student);
    }

    @DeleteMapping("/{id}")
    public Iterable<Student> deleteStudent(@PathVariable Long id) {
        return studentService.delete(id);
    }
}
