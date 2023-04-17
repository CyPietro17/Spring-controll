package it.svil.controller.student;

import it.svil.controller.course.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Optional<Student> getById(Long id) {
        return studentRepository.findById(id);
    }

    public Iterable<Student> getStudents(){
        return studentRepository.findAll();
    }

    public Student putStudent(Long id, Student student) {
        student.setId(id);
        return studentRepository.save(student);
    }


    public Iterable<Student> delete(Long id) {
        studentRepository.deleteById(id);
        return this.getStudents();
    }

    public Student addStudent(Student student){
        return studentRepository.save(student);
    }

    public Iterable<Course> coursesToDo(Student student, Iterable<Course> courses) {
        List<Course> coursesList = new ArrayList<>();
        for(Course c : courses){
            if(!student.getCourses().contains(c))
                coursesList.add(c);
        }
        return coursesList;
    }
}
