package it.svil.controller.student;

import it.svil.controller.assignment.Assignment;
import it.svil.controller.course.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        for(Assignment assignment: student.getAssignments()){
            assignment.setStudent(student);
        }
        return studentRepository.save(student);
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
}
