package it.svil.controller.course;

import it.svil.controller.student.Student;
import it.svil.controller.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Optional<Course> getById(Long id) {
        return courseRepository.findById(id);
    }

    public Course updateCourse(Long id, Course course){
        course.setId(id);
        return courseRepository.save(course);
    }

    public Course newCourse(Course course) {
        return courseRepository.save(course);
    }

    public Iterable<Course> delete(Long id) {
        Course course = courseRepository.findById(id).orElse(null);
        for(Student s : course.getStudentList()){
            s.getCourses().remove(course);
//            studentService.putStudent(s.getId(), s);
        }
        courseRepository.deleteById(id);
        return this.findAllCourse();
    }

    public Course addCourseToStudent(Long course_id, Long student_id){
        Course course = this.getById(course_id).orElse(null);
        if(course != null){
            Student student = studentService.getById(student_id).orElse(null);
            if(student != null && !student.getCourses().contains(course)){
                student.getCourses().add(course);
                studentService.putStudent(student_id, student);
                course.getStudentList().add(student);
                return this.updateCourse(course_id, course);
            }
        }
        return null;
    }
}
