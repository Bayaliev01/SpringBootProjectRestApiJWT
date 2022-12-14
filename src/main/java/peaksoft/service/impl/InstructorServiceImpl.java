package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.dto.instructor.InstructorRequest;
import peaksoft.dto.instructor.InstructorResponse;
import peaksoft.entity.Course;
import peaksoft.entity.Groups;
import peaksoft.entity.Instructor;
import peaksoft.entity.Student;
import peaksoft.mapper.instructor.InstructorRequestConverts;
import peaksoft.mapper.instructor.InstructorResponseConverts;
import peaksoft.repository.CourseRepository;
import peaksoft.repository.InstructorRepository;
import peaksoft.service.InstructorService;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {
    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;
    private final InstructorRequestConverts instructorRequestConverts;
    private final InstructorResponseConverts instructorResponseConverts;


    @Override
    public List<InstructorResponse> getAllInstructor(Long courseId) {
        return instructorResponseConverts.view(instructorRepository.findAllInstructor(courseId));
    }

    @Override
    public InstructorResponse addInstructor(Long id, InstructorRequest instructorRequest) {
        Course course = courseRepository.findById(id).get();
        Instructor instructor = instructorRequestConverts.createInstructor(instructorRequest);
        if (course.getGroups() != null) {
            for (Groups group : course.getGroups()) {
                for (Student student : group.getStudents()) {
                    instructor.plus();
                }
            }
        }
        course.addInstructors(instructor);
        instructor.setCourse(course);
        instructorRepository.save(instructor);
        return instructorResponseConverts.viewInstructor(instructor);
    }

    @Override
    public InstructorResponse getInstructorById(Long id) {
        Instructor instructor = instructorRepository.findById(id).get();
        return instructorResponseConverts.viewInstructor(instructor);
    }

    @Override
    public InstructorResponse updateInstructor(InstructorRequest instructorRequest, Long id) {
        Instructor instructor = instructorRepository.findById(id).get();
        instructorRequestConverts.updateInstructor(instructor, instructorRequest);
        instructorRepository.save(instructor);
        return instructorResponseConverts.viewInstructor(instructor);
    }

    @Override
    public InstructorResponse deleteInstructor(Long id) {
        Instructor instructor = instructorRepository.findById(id).get();
        instructor.setCourse(null);
        instructorRepository.delete(instructor);
        return instructorResponseConverts.viewInstructor(instructor);
    }

    @Override
    public InstructorResponse assignInstructor(Long courseId, Long instructorId) throws IOException {
        Instructor instructor = instructorRepository.findById(instructorId).get();
        Course course = courseRepository.findById(courseId).get();
        if (course.getInstructors() != null) {
            for (Instructor g : course.getInstructors()) {
                if (g.getId() == instructorId) {
                    throw new IOException("This instructor already exists!");
                }
            }
        }
        for (Groups g : instructor.getCourse().getGroups()) {
            for (Student s : g.getStudents()) {
                instructor.minus();
            }
        }
        for (Groups g : course.getGroups()) {
            for (Student s : g.getStudents()) {
                instructor.plus();
            }
        }
        instructor.getCourse().getInstructors().remove(instructor);
        instructor.setCourse(course);
        course.addInstructors(instructor);
        instructorRepository.save(instructor);
        courseRepository.save(course);
        return instructorResponseConverts.viewInstructor(instructor);
    }

    @Override
    public List<InstructorResponse> listAllInstructors() {
        return instructorResponseConverts.view(instructorRepository.addInstructor());
    }
}
