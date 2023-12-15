package com.ltp.gradesubmission;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Grade;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.repository.CourseRepository;
import com.ltp.gradesubmission.repository.GradeRepository;
import com.ltp.gradesubmission.repository.StudentRepository;

@SpringBootApplication
public class GradeSubmissionApplication implements CommandLineRunner {

	@Autowired
	StudentRepository studentRepository;
	@Autowired
	GradeRepository gradeRepository;
	@Autowired
	CourseRepository courseRepository;

	public static void main(String[] args) {
		SpringApplication.run(GradeSubmissionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Student students[] = new Student[4];
		Course courses[] = new Course[5];
		Grade grades[] = new Grade[5];

		initializeStudents(students);
		initializeCourses(courses);
		//initializeGrades(grades,courses,students);

		for (Student s : students) {
			s.setUniqueId(UUID.randomUUID().toString());
			studentRepository.save(s);
		}
		for(Course c:courses){
			courseRepository.save(c);
		}
	}

	private void initializeGrades(Grade[] grades, Course[] courses,Student[] students) {
		grades [0] = new Grade(1L, "A+", students[0], courses[0]);
		grades [1] = new Grade(2L, "A", students[0], courses[1]);
		grades [2] = new Grade(3L, "A-", students[0], courses[2]);
		grades [3] = new Grade(4L, "A", students[0], courses[3]);
		grades [4] = new Grade(5L, "B+", students[0], courses[4]);
	}

	private void initializeCourses(Course[] courses) {
		courses [0] = new Course("Algorithm Analysis", "CSC645", "learn how to write and design efficient algorithm");
		courses [1] = new Course("Data structures", "CSC508","you will learn a lot of data structures to get you prepared for coding interview");
		courses [2] = new Course("Parallel Processing", "CSC580","learn how to run your program using multi processor. the only catch for this course is it uses old technology. so its a bit boring");
		courses [3] = new Course("Data Science with Python", "CSC649","nothing fancy, just a glorified statistic subject that uses a little bit of programming.");
		courses [4] = new Course("Mobile Programming", "CSC557","learn how to develop mobile application using Android Studio!");
	}

	private void initializeStudents(Student[] students) {

		students[0] = new Student("Radhi Fauzan", LocalDate.parse("1998-11-10"));
		students[1] = new Student("Aqilah Nadhirah", LocalDate.parse("1998-09-29"));
		students[2] = new Student("Hadif Samry", LocalDate.parse("1998-11-26"));
		students[3] = new Student("Munir Zaki", LocalDate.parse("1998-11-03"));
	}

}
