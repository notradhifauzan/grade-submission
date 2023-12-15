package com.ltp.gradesubmission.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "unique_id")
    private String uniqueId;

    @NonNull
    @NotBlank(message = "Name cannot be blank")
    @Column(name = "name",nullable = false)
    private String name;

    @NonNull // normally associate with db
    @NotNull(message = "Birth date cannot be blank")
    @Past(message = "The birth date must be in the past")
    @Column(name = "birth_date",nullable = false)
    private LocalDate birthDate;

    /*
     * 1 student associated with many grades
     * 
     * 'mappedBy' attribute should refer to the owning side attribute
     */
    @JsonIgnore
    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    private List<Grade> grades;

    /*
     * one student can enroll in many courses
     * one course can have many students
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "students", cascade = CascadeType.ALL)
    private List<Course> courses;

    @JsonIgnore
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private ImageData profileImage;
}
