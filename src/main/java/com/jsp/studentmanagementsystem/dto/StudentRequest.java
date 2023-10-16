package com.jsp.studentmanagementsystem.dto;

public class StudentRequest {
	
	private String studentName;
	private String studentEmail;
	private long studentPhno;
	private String studentGrade;
	private String password;
	
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentEmail() {
		return studentEmail;
	}
	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}
	public long getStudentPhno() {
		return studentPhno;
	}
	public void setStudentPhno(long studentPhno) {
		this.studentPhno = studentPhno;
	}
	public String getStudentGrade() {
		return studentGrade;
	}
	public void setStudentGrade(String studentGrade) {
		this.studentGrade = studentGrade;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
