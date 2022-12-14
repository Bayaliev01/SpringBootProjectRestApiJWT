package peaksoft.service;

import peaksoft.dto.student.StudentRequest;
import peaksoft.dto.student.StudentResponse;
import peaksoft.entity.Student;


import java.io.IOException;
import java.util.List;

public interface StudentService {
    List<StudentResponse> getAllStudents(Long id);

    List<StudentResponse> listAllStudents();

    StudentResponse addStudent(Long id, StudentRequest studentRequest);

    StudentResponse getStudentById(Long id);

    StudentResponse updateStudent(StudentRequest studentRequest, Long id);

    StudentResponse deleteStudent(Long id);

    StudentResponse assignStudent(Long groupId, Long studentId) throws IOException;
}
