package com.shiv.service;

import java.util.List;

import com.shiv.models.Student;

import io.reactivex.Flowable;

public interface DataService {
	
	public List<Student> getStudentInformationByDepartment(String dept_name);

	public Flowable<String> getStudentsDepartment(String name);
	
	public Flowable<Integer> getStudentAge(String name);
}
