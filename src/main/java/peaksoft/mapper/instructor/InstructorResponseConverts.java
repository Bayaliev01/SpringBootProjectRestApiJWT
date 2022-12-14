package peaksoft.mapper.instructor;

import org.springframework.stereotype.Component;
import peaksoft.dto.group.GroupsResponse;
import peaksoft.dto.instructor.InstructorResponse;
import peaksoft.entity.Groups;
import peaksoft.entity.Instructor;

import java.util.ArrayList;
import java.util.List;

@Component
public class InstructorResponseConverts {
    public InstructorResponse viewInstructor(Instructor instructor) {
        if (instructor == null) {
            return null;
        }
        InstructorResponse instructorResponse = new InstructorResponse();

        instructorResponse.setId(instructor.getId());
        instructorResponse.setLastName(instructor.getLastName());
        instructorResponse.setFirstName(instructor.getFirstName());
        instructorResponse.setEmail(instructor.getEmail());
        instructorResponse.setStudents(instructor.getStudents());
        return instructorResponse;
    }

    public List<InstructorResponse> view(List<Instructor> instructors) {
        List<InstructorResponse> instructorResponseList = new ArrayList<>();
        for (Instructor instructor : instructors) {
            instructorResponseList.add(viewInstructor(instructor));
        }
        return instructorResponseList;
    }
}
