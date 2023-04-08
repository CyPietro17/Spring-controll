package it.svil.controller.course;

import it.svil.controller.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public Iterable<Course> findAllCourse() {
        return courseService.findAllCourse();
    }

    @PostMapping("/{course_id}")
    public Course addCourse(@PathVariable Long course_id, @RequestBody List<Long> students_id) {
        return courseService.addCourseToStudent(course_id, students_id);
    }
}
