package com.ltp.gradesubmission;

import java.time.LocalDate;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Grade;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.repository.CourseRepository;
import com.ltp.gradesubmission.repository.GradeRepository;
import com.ltp.gradesubmission.repository.StudentRepository;

@SpringBootTest
@AutoConfigureMockMvc
class GradeSubmissionApplicationTests {

	@Autowired
	StudentRepository studentRepository;
	@Autowired
	CourseRepository courseRepository;
	@Autowired
	GradeRepository gradeRepository;

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;

	private Student students[] = new Student[] {
		new Student("Radhi Fauzan", LocalDate.parse("1998-11-10")),
		new Student("Aqilah Nadhirah", LocalDate.parse("1998-09-29")),
		new Student("Hadif Samry", LocalDate.parse("1998-11-26")),
		new Student("Munir Zaki", LocalDate.parse("1998-11-03"))
	};

	private Course courses[] = new Course[] {
		new Course("Programming I", "CSC402", "Introduction to programming using C++"),
		new Course("Programming II", "CSC404", "Intermediatte programming using C++")
	};

	private Grade grades[] = new Grade[]{
		new Grade(1L, "A+", students[0], courses[0]),
		new Grade(1L, "A+", students[0], courses[1]),
	};

	@BeforeEach
	void setup(){
		for(Student s:students){
			studentRepository.save(s);
		}
		for(Course c:courses){
			courseRepository.save(c);
		}
		for(Grade g:grades){
			gradeRepository.save(g);
		}
	}

	@AfterEach
	void clear(){
		studentRepository.deleteAll();
		courseRepository.deleteAll();
		gradeRepository.deleteAll();
	}

	@Test
	public void validStudentCreation() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders.post("/student")
		.contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(new Student("Akmal", LocalDate.parse("2004-01-01"))));

		mockMvc.perform(request)
		.andExpect(status().isCreated());
	}

	@Test
	public void invalidStudentCreation() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/student")
		.contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(new Student("", LocalDate.parse("2000-01-01"))));

		mockMvc.perform(request)
		.andExpect(status().isBadRequest());
	}

}
