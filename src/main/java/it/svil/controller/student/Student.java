package it.svil.controller.student;

import com.fasterxml.jackson.annotation.JsonBackReference;
import it.svil.controller.assignment.Assignment;
import it.svil.controller.course.Course;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    private String first_name;

    private String last_name;

    @Column(unique = true)
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth_date;

    @OneToMany(mappedBy = "student")
    private List<Assignment> assignments = new ArrayList<>();

    @ManyToMany
    @JoinTable( name = "students_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses = new ArrayList<>();
}
