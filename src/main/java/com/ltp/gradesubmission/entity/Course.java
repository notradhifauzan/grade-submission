package com.ltp.gradesubmission.entity;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @NotBlank(message = "Subject cannot be blank")
    @Column(name = "subject",nullable = false)
    private String subject;

    @NonNull
    @NotBlank(message = "Code cannot be blank")
    @Column(name = "code",nullable = false, unique = true)
    private String code;

    @NonNull
    @NotBlank(message = "Description cannot be blank")
    @Column(name = "description",nullable = false)
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
    private List<Grade> grades;

    /*
     * one course can contain many students
     * one student can enroll in many courses
     */
    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "course_student",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name= "student_id")
    )
    private Set<Student> students;
}
