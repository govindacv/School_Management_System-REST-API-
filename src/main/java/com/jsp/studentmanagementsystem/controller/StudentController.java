package com.jsp.studentmanagementsystem.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jsp.studentmanagementsystem.dto.MessageData;
import com.jsp.studentmanagementsystem.dto.StudentRequest;
import com.jsp.studentmanagementsystem.dto.StudentResponse;
import com.jsp.studentmanagementsystem.entity.Student;
import com.jsp.studentmanagementsystem.service.StudentService;
import com.jsp.studentmanagementsystem.util.ResponseStructure;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentController {
	
//	@Autowired
//	Student student;
	
	@Autowired
	private StudentService service;
	
	//@RequestMapping(method = RequestMethod.POST, value = "student"
    //@PostMapping("/student")
	@PostMapping
	public ResponseEntity<ResponseStructure<StudentResponse>> saveStudent(@Valid @RequestBody StudentRequest student)
	{
		return service.saveStudent(student);
	}
     @PutMapping("/{studentId}")
     public ResponseEntity<ResponseStructure<StudentResponse>> updateStudent(@RequestBody StudentRequest student,@PathVariable int studentId)
     {
    	 return service.updateStudent(student, studentId);
     }
     @DeleteMapping("/{studentId}")
     public ResponseEntity<ResponseStructure<StudentResponse>> deleteStudent(@PathVariable int studentId)
     {
    	 return service.deleteStudent(studentId);
     }
     
     @DeleteMapping("/deleteAll")
     public ResponseEntity<ResponseStructure<StudentResponse>> deleteALLStudent()
     {
    	 return service.deleteALLStudent();
     }
     
     @GetMapping("/id/{studentId}")
 	public ResponseEntity<ResponseStructure<StudentResponse>> findStudent(@RequestParam int studentId) {
 		return service.findStudent(studentId);
 	}
     
     @GetMapping("/getAll")
     public ResponseEntity<ResponseStructure<List<Student>>> findAllStudent()
     {
    	 ResponseEntity<ResponseStructure<java.util.List<Student>>> findAll = service.findAllStudent();
		 return findAll;
     }
     
     @GetMapping("/email")
     public ResponseEntity<ResponseStructure<StudentResponse>> findByStudentEmail(@RequestParam String studentEmail)
     {
    	
    	 return service.findByStudentEmail(studentEmail);
     }
     
     @GetMapping("/phno")
     public ResponseEntity<ResponseStructure<StudentResponse>> findByPhno(@RequestParam long studentPhno)
     {
    	 return service.findByStudentPhno(studentPhno);
     }
     
     @GetMapping("/grade")
     public ResponseEntity<ResponseStructure<List<String>>> getAllEmailByGrade(@RequestParam String grade)
     {
    	 return service.getAllEmailsByGrade(grade);
     }
     
     @PostMapping("/extract")
     public ResponseEntity<String> ExtractDataFromExcel(@RequestParam MultipartFile file) throws IOException
     {
    	 return service.extractDataFromExcel(file);
     }
     
     @PostMapping("/write/excel")
     public ResponseEntity<String> writeToExcel(@RequestParam String filePath) throws IOException
     {
    	 return service.writeToExcel(filePath);
     }
     
     @PostMapping("/extractcsv")
     public ResponseEntity<ResponseStructure<List<StudentResponse>>> ExtractDataFromCsv(@RequestParam MultipartFile file) throws IOException
     {
    	 return service.ectractDataFromCsv(file);
     }
     
     @PostMapping("/mail")
     public ResponseEntity<String> sendMail(@RequestBody  MessageData messageData) 
     {
    	 
    	 return service.sendMail(messageData);
     }
     
     @PostMapping("/mime-message")
     public ResponseEntity<String> sendMimeMessage(@RequestBody MessageData messageData) throws MessagingException
     {
    	 return service.sendMimeMessage(messageData);
     }
     
     

}