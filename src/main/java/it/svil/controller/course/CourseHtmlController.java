package it.svil.controller.course;

import it.svil.controller.security.model.MyUser;
import it.svil.controller.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/courses")
public class CourseHtmlController {

    private final CourseService courseService;
    private final StudentService studentService;
    private Long tmp;

    @Autowired
    public CourseHtmlController(CourseService courseService, StudentService studentService){
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @GetMapping
    public String getAllCourses(Model model, Authentication authentication){
        model.addAttribute("courses", courseService.findAllCourse());
        if(authentication != null)
            model.addAttribute("user", (MyUser) authentication.getPrincipal());
        return "courses";
    }

    @GetMapping("/updateCourse/{id}")
    public String add(@PathVariable Long id, Model model, Authentication authentication) {
        Course course = courseService.getById(id).orElse(null);
        model.addAttribute("course", course);
        if(authentication != null)
            model.addAttribute("user", (MyUser) authentication.getPrincipal());
        return "put-course";
    }

    @PostMapping("/putCourse/{id}")
    public String putCourse(@PathVariable Long id, Course course){
        System.out.println("Modifico lo studente " + course);
        courseService.updateCourse(id, course);
        return "redirect:/courses";
    }

    @GetMapping("/addCourse")
    public String add(Model model, Authentication authentication) {
        model.addAttribute("course", new Course());
        if(authentication != null)
            model.addAttribute("user", (MyUser) authentication.getPrincipal());
        return "add-course";
    }

    @PostMapping("/add")
    public String add(Course course){
        System.out.println("Nuovo Corso: " + course);
        courseService.newCourse(course);
        return "redirect:/courses";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        courseService.getById(id).orElseThrow(
                () -> new IllegalArgumentException("Il corso " + id + " non esiste")
        );
        System.out.println("Il corso " + id + " è stato eliminato");
        courseService.delete(id);
        return "redirect:/courses";
    }
}
