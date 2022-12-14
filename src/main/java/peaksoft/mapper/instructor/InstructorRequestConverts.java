package peaksoft.mapper.instructor;

import org.springframework.stereotype.Component;
import peaksoft.dto.group.GroupRequest;
import peaksoft.dto.instructor.InstructorRequest;
import peaksoft.entity.Groups;
import peaksoft.entity.Instructor;

@Component
public class InstructorRequestConverts {

    public Instructor createInstructor(InstructorRequest instructorRequest) {
        if (instructorRequest == null) {
            return null;
        }
        Instructor instructor = new Instructor();
        instructor.setFirstName(instructorRequest.getFirstName());
        instructor.setLastName(instructorRequest.getLastName());
        instructor.setPhoneNumber(instructorRequest.getPhoneNumber());
        instructor.setEmail(instructorRequest.getEmail());
        instructor.setSpecialization(instructorRequest.getSpecialization());
        return instructor;
    }

    public void updateInstructor(Instructor instructor, InstructorRequest instructorRequest) {
        instructor.setFirstName(instructorRequest.getFirstName());
        instructor.setLastName(instructorRequest.getLastName());
        instructor.setPhoneNumber(instructorRequest.getPhoneNumber());
        instructor.setEmail(instructorRequest.getEmail());
        instructor.setSpecialization(instructorRequest.getSpecialization());
    }
}
