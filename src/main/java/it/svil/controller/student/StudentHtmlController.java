package it.svil.controller.student;

import it.svil.controller.course.CourseService;
import it.svil.controller.security.model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentHtmlController {

    private final StudentService studentService;
    private final CourseService courseService;
    private Long tmp;

    @Autowired
    public StudentHtmlController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping
    public String getAllStudent(Model model, Authentication authentication) {
        model.addAttribute("students", studentService.getStudents());
        if(authentication != null)
            model.addAttribute("user", (MyUser) authentication.getPrincipal());
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
    public String add(Model model, Authentication authentication) {
        model.addAttribute("student", new Student());
        if(authentication != null)
            model.addAttribute("user", (MyUser) authentication.getPrincipal());
        return "add-student";
    }

    @PostMapping("/add")
    public String add(Student student){
        System.out.println("Inserisco lo studente " + student);
        studentService.addStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/put/{id}")
    public String putStudent(@PathVariable Long id, Model model, Authentication authentication) {
        Student student = studentService.getById(id).orElseThrow(
                () -> new IllegalArgumentException("Lo studente " + id + " non esiste")
        );
        model.addAttribute("student", student);
        if(authentication != null)
            model.addAttribute("user", (MyUser) authentication.getPrincipal());
        return "put-student";
    }

    @PostMapping("/update/{id}")
    public String put(@PathVariable Long id, Student student){
        System.out.println("Modifico lo studente " + student);
        studentService.putStudent(id, student);
        return "redirect:/students";
    }

    @GetMapping("/courses/{id}")
    public String coursesSubscribeStudent(@PathVariable Long id, Model model, Authentication authentication){
        Student student = studentService.getById(id).orElse(null);
        model.addAttribute("student", student);
        model.addAttribute("courses", student.getCourses());
        if(authentication != null)
            model.addAttribute("user", (MyUser) authentication.getPrincipal());
        return "courses-of-student";
    }

    @GetMapping("/coursesToDo/{id}")
    public String coursesToDoStudent(@PathVariable Long id, Model model, Authentication authentication){
        Student student = studentService.getById(id).orElse(null);
        model.addAttribute("student", student);
        model.addAttribute("courses", studentService.coursesToDo(student, courseService.findAllCourse()));
        if(authentication != null)
            model.addAttribute("user", (MyUser) authentication.getPrincipal());
        this.tmp = student.getId();
        return "courses-to-do";
    }

    @GetMapping("/subscribe/{c_id}")
    public String subscribeStudent(@PathVariable Long c_id){
        courseService.addCourseToStudent(c_id, this.tmp);
        return "redirect:/students/coursesToDo/"+this.tmp;
    }
}
