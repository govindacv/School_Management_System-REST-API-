package com.jsp.studentmanagementsystem.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jsp.studentmanagementsystem.dto.MessageData;
import com.jsp.studentmanagementsystem.dto.StudentRequest;
import com.jsp.studentmanagementsystem.dto.StudentResponse;
import com.jsp.studentmanagementsystem.entity.Student;
import com.jsp.studentmanagementsystem.util.ResponseStructure;

import jakarta.mail.MessagingException;

@Service
public interface StudentService {
	public ResponseEntity<ResponseStructure<StudentResponse>> saveStudent(StudentRequest student);

	public ResponseEntity<ResponseStructure<StudentResponse>> updateStudent(StudentRequest studentRequest,
			int studentId);

	public ResponseEntity<ResponseStructure<StudentResponse>> deleteStudent(int studentId);

	public ResponseEntity<ResponseStructure<StudentResponse>> findStudent(int studentId);

	public ResponseEntity<ResponseStructure<List<Student>>> findAllStudent();

	public ResponseEntity<ResponseStructure<StudentResponse>> findByStudentEmail(String studentEmail);

	public ResponseEntity<ResponseStructure<StudentResponse>> findByStudentPhno(long studentPhno);

	public ResponseEntity<ResponseStructure<List<String>>> getAllEmailsByGrade(String grade);

	public ResponseEntity<String> extractDataFromExcel(MultipartFile file) throws IOException;

	public ResponseEntity<String> writeToExcel(String filePath) throws IOException;

	public ResponseEntity<ResponseStructure<List<StudentResponse>>> ectractDataFromCsv(MultipartFile file) throws IOException;

	public ResponseEntity<String> sendMail(MessageData messageData);

	public ResponseEntity<String> sendMimeMessage(MessageData messageData) throws MessagingException;

	public ResponseEntity<ResponseStructure<StudentResponse>> deleteALLStudent();

}
