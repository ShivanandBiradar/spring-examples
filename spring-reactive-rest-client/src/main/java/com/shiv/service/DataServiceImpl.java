package com.shiv.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiv.dao.StudentInfoClient;
import com.shiv.models.Student;
import com.shiv.models.StudentsInformationDetails;

import io.reactivex.Flowable;

import static com.shiv.constants.LogMessageConstants.*;

@Service
public class DataServiceImpl implements DataService {

    private static final Logger LOG = LoggerFactory.getLogger(DataServiceImpl.class);

	@Autowired
	private StudentInfoClient studentInfoClient;

    @Autowired
    private ObjectMapper mapper;

	@Override
	public List<Student> getStudentInformationByDepartment(String dept_name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flowable<String> getStudentsDepartment(String name) {
		
		return this.studentInfoClient.getStudentsInformation(name).flatMap(response -> this.convertResponse(response, StudentsInformationDetails.class))
        .map(this::getDepartment);
	}

	@Override
	public Flowable<Integer> getStudentAge(String name) {
		return this.studentInfoClient.getStudentsInformation(name).flatMap(response -> this.convertResponse(response, StudentsInformationDetails.class))
		        .map(this::getAge);
	}

    private Student getStudentDetails(final StudentsInformationDetails infoDetails) {
    	
        return infoDetails.getStudents().stream()
                .findFirst().orElseThrow(() -> new RuntimeException(INVALID_STUDENT_DETAILS));
                
    }
    
    private String getDepartment(final StudentsInformationDetails infoDetails) {
    	
        return infoDetails.getStudents().stream()
                .findFirst().orElseThrow(() -> new RuntimeException(INVALID_STUDENT_DETAILS))
                .getDepartment();
    }

    private String getAge(final StudentsInformationDetails infoDetails) {
    	
        return infoDetails.getStudents().stream()
                .findFirst().orElseThrow(() -> new RuntimeException(INVALID_STUDENT_DETAILS))
                .getAge();
    }
    public <T> Flowable<T> convertResponse(final HttpResponse<JsonNode> response, Class<T> entityClass) {
    	
        try {
            final T serializedResponse = mapper.readValue(response.body().toString(), entityClass);
            return Flowable.just(serializedResponse);
        } catch (Exception e) {
            final String msg = RESPONSE_SERIALIZE_ERROR + e.getMessage();
            LOG.error(msg);
            return Flowable.error(new RuntimeException(msg));
        }
    }
}
