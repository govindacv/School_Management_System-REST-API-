package com.jsp.studentmanagementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.jsp.studentmanagementsystem.entity.Student;

public interface StudentRepo extends JpaRepository<Student, Integer> {
	public Student findByStudentEmail(String studentEmial);

	public Student findByStudentPhno(long studentPhno);

	@Query("select s.studentEmail from Student s where s.studentGrade=?1")
	public List<String> getEmailsByGrade(String grade);
}