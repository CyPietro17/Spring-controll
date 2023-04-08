package it.svil.controller.assignment;

import it.svil.controller.student.Student;
import it.svil.controller.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssignmentService {

    private AssignmentRepository assignmentRepository;
    private StudentService studentService;

    @Autowired
    public AssignmentService(AssignmentRepository repository, StudentService studentService){
        this.assignmentRepository = repository;
        this.studentService = studentService;
    }

    public Iterable<Assignment> getAssignments(){
        return assignmentRepository.findAll();
    }

    public Optional<Assignment> AssignmentById(Long id){
        return assignmentRepository.findById(id);
    }

    public Assignment add(Long studentId, Assignment assignment){
        Student student = studentService.getById(studentId).orElse(null);
        if(student != null) {
            assignment.setStudent(student);
        }
        return assignmentRepository.save(assignment);
    }
}
