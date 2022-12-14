package peaksoft.service;

import peaksoft.dto.instructor.InstructorRequest;
import peaksoft.dto.instructor.InstructorResponse;
import peaksoft.entity.Instructor;


import java.io.IOException;
import java.util.List;

public interface InstructorService {
    List<InstructorResponse> getAllInstructor(Long courseId);

    InstructorResponse addInstructor(Long id, InstructorRequest instructorRequest);

    InstructorResponse getInstructorById(Long id);

    InstructorResponse updateInstructor(InstructorRequest instructorRequest, Long id) throws IOException;

    InstructorResponse deleteInstructor(Long id);

    InstructorResponse assignInstructor(Long courseId, Long instructorId) throws IOException;

    List<InstructorResponse> listAllInstructors();
}
