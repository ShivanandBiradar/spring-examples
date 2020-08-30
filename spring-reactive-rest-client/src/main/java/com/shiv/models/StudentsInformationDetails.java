package com.shiv.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentsInformationDetails {

    @JsonProperty("students")
	private List<Student> students;

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}
    
}
