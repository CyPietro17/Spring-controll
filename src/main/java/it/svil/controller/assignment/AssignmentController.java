package it.svil.controller.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/assignment")
public class AssignmentController {

    private AssignmentService service;

    @Autowired
    public AssignmentController(AssignmentService service){
        this.service = service;
    }

    @GetMapping
    public Iterable<Assignment> getAssignment(){
        return service.getAssignments();
    }

    @GetMapping("/{id}")
    public Optional<Assignment> getAsById(Long id){
        return service.AssignmentById(id);
    }

    @PostMapping("{student_id}")
    public Assignment addAssignment(@PathVariable Long student_id, @RequestBody Assignment assignment){
        return service.add(student_id, assignment);
    }
}
