package com.javatech.student.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.javatech.student.entity.Student;
import com.javatech.student.service.StudentService;

@Controller
public class StudentController {

	final static Logger logger = LoggerFactory.getLogger(StudentController.class);

	private StudentService studentService;

	public StudentController(StudentService studentService) {
		logger.info("student service object initialization");
		this.studentService = studentService;
		logger.info("student service object created");
	}

	@GetMapping("/students")
	public String listStudents(Model model) {
		logger.info("Inside listStudents() - Controller class");
		model.addAttribute("students", studentService.getAllStudents());
		logger.info("Model Object for list of students" + model.getAttribute("students"));
		return "students";
	}

	@GetMapping("/students/new")
	public String createStudentForm(Model model) {
		logger.info("Inside createStudentForm() - Controller class");
		Student student = new Student();
		model.addAttribute("student", student);
		logger.info("Model Object for create student" + model.getAttribute("student"));
		return "create_student";
	}

	@PostMapping("/students")
	public String saveStudent(@ModelAttribute("student") Student student) {
		logger.info("Inside saveStudent() - Controller class");
		Student saveStudent = studentService.saveStudent(student);
		logger.info("Student Details :" + saveStudent.getFirstName());
		return "redirect:/students";
	}

	@GetMapping("/students/edit/{id}")
	public String editStudentForm(@PathVariable Long id, Model model) {
		logger.info("Inside editStudentForm() - Controller class");
		model.addAttribute("student", studentService.getStudentById(id));
		logger.info("Model Object for edit student" + model.getAttribute("student"));
		return "edit_student";
	}

	@PostMapping("/students/{id}")
	public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student student, Model model) {
		logger.info("Inside updateStudent() - Controller class");
		Student existingStudent = studentService.getStudentById(id);
		logger.info("Update the student for Id :" + existingStudent.getId());
		existingStudent.setId(id);
		existingStudent.setFirstName(student.getFirstName());
		existingStudent.setLastName(student.getLastName());
		existingStudent.setEmail(student.getEmail());

		studentService.updateStudent(existingStudent);
		logger.info("Student Updation Successfully Completed");
		return "redirect:/students";
	}

	@GetMapping("/students/{id}")
	public String deleteStudent(@PathVariable Long id) {
		logger.info("Inside deleteStudent() - Controller class");
		studentService.deleteStudentById(id);
		logger.info("Student Deletion Successfully Completed for Id:" + id);
		return "redirect:/students";
	}
}
