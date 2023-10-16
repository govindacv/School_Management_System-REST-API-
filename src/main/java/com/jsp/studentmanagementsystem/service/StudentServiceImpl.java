 package com.jsp.studentmanagementsystem.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jsp.studentmanagementsystem.dto.MessageData;
import com.jsp.studentmanagementsystem.dto.StudentRequest;
import com.jsp.studentmanagementsystem.dto.StudentResponse;
import com.jsp.studentmanagementsystem.entity.Student;
import com.jsp.studentmanagementsystem.exception.StudentNotFoundByEmailException;
import com.jsp.studentmanagementsystem.exception.StudentNotFoundByIdException;
import com.jsp.studentmanagementsystem.exception.StudentNotFoundByPhnoException;
import com.jsp.studentmanagementsystem.repository.StudentRepo;
import com.jsp.studentmanagementsystem.util.ResponseStructure;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepo repo;
	
	@Autowired
	private org.springframework.mail.javamail.JavaMailSender JavaMailSender;

	@Override
	public ResponseEntity<ResponseStructure<StudentResponse>> saveStudent(StudentRequest studentRequest) {

		Student student = new Student();

		student.setStudentEmail(studentRequest.getStudentEmail());
		student.setStudentGrade(studentRequest.getStudentGrade());
		student.setStudentName(studentRequest.getStudentName());
		student.setStudentPhno(studentRequest.getStudentPhno());
		student.setPassword(studentRequest.getPassword());
		Student student2 = repo.save(student);

		StudentResponse response = new StudentResponse();
		// response.setStudentId(student2.getStudentId());
		response.setStudentName(student2.getStudentName());
		response.setStudentGrade(student2.getStudentGrade());

		ResponseStructure<StudentResponse> structure = new ResponseStructure<>();
		structure.setStatus(HttpStatus.CREATED.value());
		structure.setMessage("Student data saved successfully");
		structure.setData(response);
		return new ResponseEntity<ResponseStructure<StudentResponse>>(structure, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseStructure<StudentResponse>> updateStudent(StudentRequest studentRequest,
			int studentId) {

		Optional<Student> optional = repo.findById(studentId);
		if (optional.isPresent()) {
			// creating new student entity object and setting the data request to entity
			// object
			Student student = new Student();
			student.setStudentId(studentId);
			student.setStudentName(studentRequest.getStudentName());
			student.setStudentEmail(studentRequest.getStudentEmail());
			student.setStudentPhno(studentRequest.getStudentPhno());
			student.setStudentGrade(studentRequest.getStudentGrade());

			// saving the entity object
			student = repo.save(student);

			// the returned student object must be converted to a response object
			// and then should be returned to the client

			StudentResponse response = new StudentResponse();
			response.setStudentId(student.getStudentId());
			response.setStudentName(student.getStudentName());
			response.setStudentGrade(student.getStudentGrade());

			// creating response structure
			ResponseStructure<StudentResponse> structure = new ResponseStructure<StudentResponse>();
			structure.setStatus(HttpStatus.ACCEPTED.value());
			structure.setMessage("Student data is updated successufully...!!!");
			structure.setData(response);
			return new ResponseEntity<ResponseStructure<StudentResponse>>(structure, HttpStatus.OK);
		} else {
			return null;
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<StudentResponse>> deleteStudent(int studentId) {

		Optional<Student> optional = repo.findById(studentId);

		if (optional.isPresent()) {
			Optional<Student> student = repo.findById(studentId);
			repo.deleteById(studentId);
			ResponseStructure<StudentResponse> structure = new ResponseStructure<StudentResponse>();
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setMessage("Student data deleted successfully...!!!");
			// structure.setData(optional);
			return new ResponseEntity<ResponseStructure<StudentResponse>>(structure, HttpStatus.OK);
		} else {
			throw new StudentNotFoundByIdException("The student data is not found!!");
		}
	}
	
	@Override
	public ResponseEntity<ResponseStructure<StudentResponse>> deleteALLStudent() {
		
		
			Student stud = new Student();
			
			repo.deleteAll();

			StudentResponse response=new StudentResponse();
			response.setStudentId(stud.getStudentId());
			response.setStudentName(stud.getStudentName());
			response.setStudentGrade(stud.getStudentGrade());

			ResponseStructure<StudentResponse>studet= new ResponseStructure<StudentResponse>();
			studet.setStatus(HttpStatus.ACCEPTED.value());
			studet.setMessage("ALL Data deleted Sucessfully");
			studet.setData(response);

			return new ResponseEntity<ResponseStructure<StudentResponse>>(HttpStatus.OK);
		}

	@Override
	public ResponseEntity<ResponseStructure<StudentResponse>> findStudent(int studentId) {
		Optional<Student> optFetch = repo.findById(studentId);
		if (optFetch != null) {
			Student st = optFetch.get();

			StudentResponse response = new StudentResponse();
			response.setStudentId(st.getStudentId());
			response.setStudentName(st.getStudentName());
			response.setStudentGrade(st.getStudentGrade());

			ResponseStructure<StudentResponse> structure = new ResponseStructure<StudentResponse>();
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Data found sucessfully");
			structure.setData(response);

			return new ResponseEntity<ResponseStructure<StudentResponse>>(structure, HttpStatus.FOUND);
		} else {
			return null;
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<List<Student>>> findAllStudent() {

		List<Student> findAll = (List<Student>) repo.findAll();
		ResponseStructure<List<Student>> structure = new ResponseStructure<List<Student>>();
		structure.setStatus(HttpStatus.FOUND.value());
		structure.setMessage("Data fetch Succefully..");
		structure.setData(findAll);
		// return findAll;
		return new ResponseEntity<ResponseStructure<List<Student>>>(structure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<StudentResponse>> findByStudentEmail(String studentEmail) {

		Student student = repo.findByStudentEmail(studentEmail);
		System.out.println(student);
		if (student != null) {
			StudentResponse response = new StudentResponse();
			response.setStudentId(student.getStudentId());
			response.setStudentName(student.getStudentName());
			// response.setStudentEmail(student.getStudentEmail());
			response.setStudentGrade(student.getStudentGrade());

			ResponseStructure<StudentResponse> structure = new ResponseStructure<StudentResponse>();
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Student Found based On Email");
			structure.setData(response);

			return new ResponseEntity<ResponseStructure<StudentResponse>>(structure, HttpStatus.FOUND);
		} else {
			throw new StudentNotFoundByEmailException("The student deatil is not found by mail...!!!!");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<StudentResponse>> findByStudentPhno(long studentPhno) {
		Student student = repo.findByStudentPhno(studentPhno);
		if (student != null) {
			StudentResponse response = new StudentResponse();
			response.setStudentId(student.getStudentId());
			response.setStudentName(student.getStudentName());
			response.setStudentGrade(student.getStudentGrade());

			ResponseStructure<StudentResponse> structure = new ResponseStructure<StudentResponse>();
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Student found based on phone_number...!!!");
			structure.setData(response);
			return new ResponseEntity<ResponseStructure<StudentResponse>>(structure, HttpStatus.FOUND);
		} else {
			throw new StudentNotFoundByPhnoException("Failed to find the student...!!!");
		}
	}

	@Override
	
	public ResponseEntity<ResponseStructure<List<String>>> getAllEmailsByGrade(String grade) {
		List<String> emails = repo.getEmailsByGrade(grade);
		if (emails != null) {
			repo.getEmailsByGrade(grade);

			ResponseStructure<List<String>> structure = new ResponseStructure<List<String>>();
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Student mail is found by student grade...!!!");
			// structure.setData(grade);
			return new ResponseEntity<ResponseStructure<List<String>>>(structure, HttpStatus.FOUND);
		}
		return null;
	}

	@Override
	public ResponseEntity<String> extractDataFromExcel(MultipartFile file) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		for (Sheet sheet : workbook) {
			for (Row row : sheet) {
				if (row != null) {
					String name = row.getCell(0).getStringCellValue();
					String email = row.getCell(1).getStringCellValue();
					long phoneNumber = (long) row.getCell(2).getNumericCellValue();
					String grade = row.getCell(3).getStringCellValue();
					String password = row.getCell(4).getStringCellValue();
					System.out.println(name + ", " + email + ", " + phoneNumber + ", " + grade + "," + password);
					Student student = new Student();
					student.setStudentName(name);
					student.setStudentEmail(email);
					student.setStudentGrade(grade);
					student.setPassword(password);
					student.setStudentPhno(phoneNumber);

					repo.save(student);
				}
			}
		}
		return null;
	}

	@Override
	public ResponseEntity<String> writeToExcel(String filePath) throws IOException {
		List<Student> student = repo.findAll();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();

		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("studentId");
		header.createCell(1).setCellValue("studentName");
		header.createCell(2).setCellValue("studentEmail");
		header.createCell(3).setCellValue("studentPhno");
		header.createCell(4).setCellValue("studentGrade");
		header.createCell(5).setCellValue("studentPassword");

		int rowNum = 1;
		for (Student students : student) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(students.getStudentId());
			row.createCell(1).setCellValue(students.getStudentName());
			row.createCell(2).setCellValue(students.getStudentEmail());
			row.createCell(3).setCellValue(students.getStudentPhno());
			row.createCell(4).setCellValue(students.getStudentGrade());
			row.createCell(5).setCellValue(students.getPassword());
		}

		FileOutputStream outputstream = new FileOutputStream(filePath);
		workbook.write(outputstream);
		workbook.close();

		return new ResponseEntity<String>("Data transfered to Excel sheet...!!!", HttpStatus.OK);

	}

	

	@Override
	public ResponseEntity<String> sendMail(MessageData messageData) {
		SimpleMailMessage message =  new SimpleMailMessage();
		message.setTo(messageData.getTo());
		message.setSubject(messageData.getSubject());
		message.setText(messageData.getText()
		          +"\n\nThanks & Regards"
		          +"\n"+messageData.getSenderName()
		          +"\n"+messageData.getSenderAddress());
		message.setSentDate(new Date());
		
		JavaMailSender.send(message);
		return new ResponseEntity<String>("Mail sent successfully..!!!", HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<String> sendMimeMessage(MessageData messageData) throws MessagingException
	{
		MimeMessage mimemessage = JavaMailSender.createMimeMessage() ;
		MimeMessageHelper message = new MimeMessageHelper(mimemessage, true);
		message.setTo(messageData.getTo());
		message.setSubject(messageData.getSubject());
		String emailBody=messageData.getText()+
				"<br><br><h4> Thanks & Regards </h4><br>"+
				"<h4>"+ messageData.getSenderName()+"<br>"
				 + messageData.getSenderAddress()+"</h4>"+
				"<img src=\"https://jspiders.com/_nuxt/img/logo_jspiders.3b552d0.png\" width=\"250\">";
		message.setText(emailBody, true);
		message.setSentDate(new Date());
		
		JavaMailSender.send(mimemessage);
		return new ResponseEntity<String>("Mime Mail sent successfully..!!!", HttpStatus.OK);
		
		
	}


	@Override
	public ResponseEntity<ResponseStructure<List<StudentResponse>>> ectractDataFromCsv(MultipartFile file) throws IOException 
	{
		CSVParser csvparser = CSVFormat.newFormat('@').parse(new InputStreamReader(file.getInputStream()));
		List<StudentResponse> studentresponse= new ArrayList<StudentResponse>();

       for (CSVRecord csvRecord : csvparser) 
       {
          String name=csvRecord.get(0);
		  String email=csvRecord.get(1);
		  long phonenumber=Long.parseLong(csvRecord.get(2));
		  String grade=csvRecord.get(3);
		  String password=csvRecord.get(4);
		  System.out.println( name +" , "+email+" , "+phonenumber+" , "+grade+" , "+password);
		  
		  Student student = new Student();
		  student.setStudentName(name);
		  student.setStudentEmail(email);
		  student.setStudentPhno(phonenumber);
		  student.setStudentGrade(grade);
		  student.setPassword(password);
		  
		  
		  Student stud = repo.save(student);
      		
		  StudentResponse response = new StudentResponse();
		  response.setStudentId(student.getStudentId());
		  response.setStudentName(student.getStudentName());
		  response.setStudentGrade(student.getStudentGrade());
		  studentresponse.add(response);
		  
		  
       }

       ResponseStructure<List<StudentResponse>> str = new ResponseStructure<List<StudentResponse>>();
       str.setStatus(HttpStatus.OK.value());
       str.setMessage("All Data inserted into the database from CSV file..!");
       str.setData(studentresponse);
       
       csvparser.close();
		return new ResponseEntity<ResponseStructure<List<StudentResponse>>>(str,HttpStatus.OK);
		
		
		
	}

}
