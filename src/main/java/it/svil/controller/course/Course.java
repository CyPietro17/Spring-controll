package it.svil.controller.course;

import com.fasterxml.jackson.annotation.JsonBackReference;
import it.svil.controller.student.Student;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name_course;

    private String description;

    private Byte cfu;

    @ManyToMany(mappedBy = "courses", cascade = CascadeType.PERSIST)
    @JsonBackReference
    private List<Student> studentList = new ArrayList<>();
}
