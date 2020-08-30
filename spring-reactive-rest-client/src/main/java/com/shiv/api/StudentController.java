package com.shiv.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.shiv.service.DataService;

@RestController
public class StudentController {
	
//    private static final Logger LOG = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	private DataService dataService;
	
    @GetMapping("/students/dept/{name}")
    public String getDepartments(@PathVariable("name") String name) {
    	
        return "Hello " + name + ", You belong to "+dataService.getStudentsDepartment(name);
    }
}
