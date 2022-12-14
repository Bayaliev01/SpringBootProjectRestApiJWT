package peaksoft.mapper.student;

import org.springframework.stereotype.Component;
import peaksoft.dto.student.StudentRequest;
import peaksoft.entity.Student;

@Component
public class StudentRequestConverts {

    public Student createStudent(StudentRequest studentRequest) {
        if (studentRequest == null) {
            return null;
        }
        Student student = new Student();

        student.setFirstName(studentRequest.getFirstName());

        student.setLastName(studentRequest.getLastName());

        student.setEmail(studentRequest.getEmail());

        student.setPhoneNumber(studentRequest.getPhoneNumber());

        student.setStudyFormat(studentRequest.getStudyFormat());

        return student;
    }

    public void updateStudent(Student student, StudentRequest studentRequest) {
        if (studentRequest.getFirstName() != null) {
            student.setFirstName(studentRequest.getFirstName());
        } else if (studentRequest.getLastName() != null) {
            student.setLastName(studentRequest.getLastName());
        } else if (studentRequest.getEmail() != null) {
            student.setEmail(studentRequest.getEmail());
        } else if (studentRequest.getPhoneNumber() != null) {
            student.setPhoneNumber(studentRequest.getPhoneNumber());
        } else if (studentRequest.getStudyFormat() != null) {
            student.setStudyFormat(studentRequest.getStudyFormat());
        }
    }
}

