package it.svil.controller.course;

import it.svil.controller.student.Student;
import it.svil.controller.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private CourseRepository courseRepository;
    private StudentService studentService;

    @Autowired
    public CourseService(CourseRepository courseRepository, StudentService studentService){
        this.courseRepository = courseRepository;
        this.studentService = studentService;
    }

    public Iterable<Course> findAllCourse() {
        return courseRepository.findAll();
    }

    public Course addCourseToStudent(Long course_id, List<Long> stundents_id){
        Course course = this.findById(course_id).orElse(null);
        if(course != null){
            for(Long s_id: stundents_id){
                Student student = studentService.getById(s_id).orElse(null);
                if(student != null){
                   student.getCourses().add(course);
                   /*Se viene mappata la classe Student*/
//                    course.getStudentList().add(student);
                }
            }
            return courseRepository.save(course);
        }
        return null;
    }

    public Optional<Course> findById(Long id){
        return courseRepository.findById(id);
    }

//    public Course addCourse(Long student_id, Course course) {
//        Student student = studentService.getById(student_id).orElse(null);
//        if(student != null){
//            student.getCourses().add(course);
//            return courseRepository.save(course);
//        }
//        return null;
//    }
}
