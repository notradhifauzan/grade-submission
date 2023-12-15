package com.ltp.gradesubmission.entity;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.ltp.gradesubmission.validation.Score;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "grade", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"student_id","course_id"})
})
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "score")
    @Score(message = "Not a valid score")
    @NotBlank(message = "Score cannot be blank")
    private String score;

    /*
     * many grades belong to one student
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id",referencedColumnName = "id")
    private Student student;

    /*
     * many grades belong to one course
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id",referencedColumnName = "id")
    private Course course;
}
