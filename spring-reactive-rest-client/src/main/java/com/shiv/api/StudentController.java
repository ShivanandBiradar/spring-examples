package com.shiv.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.reactivex.Flowable;

import com.shiv.service.DataService;

@RestController
public class StudentController {
	
    private static final Logger LOG = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	private DataService dataService;
	
	private String result;
	
    @GetMapping("/students/dept/{name}")
    public String getDepartments(@PathVariable("name") String name) {

    	dataService.getStudentsDepartment(name).subscribe((dept,err)->{
    		if(err != null) { result=" not found!"; } else { result=dept; } });
    	
       return "Hello " + name + ", Your department is "+result;
    }
}
