package it.svil.controller.student;

import it.svil.controller.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentHtmlController {

    private final StudentService studentService;

    @Autowired
    public StudentHtmlController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String getAllStudent(Model model, Authentication authentication) {
        model.addAttribute("students", studentService.getStudents());
        if(authentication != null)
            model.addAttribute("user", (User) authentication.getPrincipal());
        return "student";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.getById(id).orElseThrow(
                () -> new IllegalArgumentException("Lo studente " + id + " non esiste")
        );
        studentService.delete(id);
        return "redirect:/students";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("student", new Student());
        return "add-student";
    }

    @PostMapping("/add")
    public String add(Student student){
        System.out.println("Inserisco lo studente " + student);
        studentService.addStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/put/{id}")
    public String putStudent(@PathVariable Long id, Model model) {
        Student student = studentService.getById(id).orElseThrow(
                () -> new IllegalArgumentException("Lo studente " + id + " non esiste")
        );
        model.addAttribute("student", student);
        return "put-student";
    }

    @PostMapping("/update/{id}")
    public String put(@PathVariable Long id, Student student){
        System.out.println("Modifico lo studente " + student);
        studentService.putStudent(id, student);
        return "redirect:/students";
    }

//    @PostMapping
//    public String postStudent() {
//        return "student";
//    }
/*********Metodo Generico**********/
//    @RequestMapping
//    public String requestStudent() {
//        return "student";
//    }
}
