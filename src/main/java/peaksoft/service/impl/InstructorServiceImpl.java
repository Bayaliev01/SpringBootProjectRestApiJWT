package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import peaksoft.dto.instructor.InstructorRequest;
import peaksoft.dto.instructor.InstructorResponse;
import peaksoft.entity.*;
import peaksoft.mapper.instructor.InstructorRequestConverts;
import peaksoft.mapper.instructor.InstructorResponseConverts;
import peaksoft.repository.CourseRepository;
import peaksoft.repository.InstructorRepository;
import peaksoft.repository.RoleRepository;
import peaksoft.repository.UserRepository;
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
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Override
    public List<InstructorResponse> getAllInstructor(Long courseId) {
        Course course = getCourseId(courseId);
        return instructorResponseConverts.view(instructorRepository.findAllInstructor(courseId));
    }

    @Override
    public InstructorResponse addInstructor(Long id, InstructorRequest instructorRequest) throws IOException {
        for (User user: userRepository.findAll()) {
            if (user.getEmail().equals(instructorRequest.getEmail())){
                throw new IOException("This instructor already exists!");
            }
        }

        User user = new User();
        Role role = roleRepository.findById(3L).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role с такой id " + id + " не существует"));
        user.setPassword(passwordEncoder.encode(instructorRequest.getPassword()));
        user.setFirstName(instructorRequest.getFirstName());
        user.setEmail(instructorRequest.getEmail());
        user.setRole(role);
        role.getUsers().add(user);

        Instructor instructor = instructorRequestConverts.createInstructor(instructorRequest);
        Course course = getCourseId(id);
        if (course.getGroups()!=null){
            for (Groups group : course.getGroups()) {
                for (Student student: group.getStudents()) {
                    instructor.plus();
                }
            }
        }


        validator(instructorRequest.getPhoneNumber().replace(" ", ""),
                instructorRequest.getLastName().replace(" ", ""),
                instructorRequest.getFirstName().replace(" ", ""));
        course.addInstructors(instructor);
        instructor.setCourse(course);
        instructor.setUser(user);
        instructorRepository.save(instructor);
        userRepository.save(user);
        roleRepository.save(role);
        return instructorResponseConverts.viewInstructor(instructor);
    }

    @Override
    public InstructorResponse getInstructorById(Long id) {
        Instructor instructor = getInstructorId(id);
        return instructorResponseConverts.viewInstructor(instructor);
    }

    @Override
    public InstructorResponse updateInstructor(InstructorRequest instructorRequest, Long id) throws IOException {
        validator(instructorRequest.getPhoneNumber()
                        .replace(" ", ""),
                instructorRequest.getLastName().replace(" ", ""),
                instructorRequest.getFirstName().replace(" ", ""));
        Instructor instructor = getInstructorId(id);
        instructorRequestConverts.updateInstructor(instructor, instructorRequest);
        User user = instructor.getUser();
        if (instructorRequest.getEmail() != null)
            user.setEmail(instructorRequest.getEmail());

        if (instructorRequest.getPassword() != null)
            user.setPassword(instructorRequest.getPassword());

        if (instructorRequest.getFirstName() != null)
            user.setFirstName(instructorRequest.getFirstName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        instructorRepository.save(instructor);
        userRepository.save(user);
        return instructorResponseConverts.viewInstructor(instructor);
    }

    @Override
    public InstructorResponse deleteInstructor(Long id) {
        Instructor instructor = getInstructorId(id);
        Role role = instructor.getUser().getRole();
        role.getUsers().remove(instructor.getUser());
        userRepository.delete(instructor.getUser());
//        instructor.setCourse(null);
        instructorRepository.delete(instructor);
        return instructorResponseConverts.viewInstructor(instructor);
    }

    @Override
    public InstructorResponse assignInstructor(Long courseId, Long instructorId) throws IOException {
        Instructor instructor = getInstructorId(instructorId);
        Course course = getCourseId(courseId);
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


    private void validator(String phoneNumber, String firstName, String lastName) throws IOException {
        if (firstName.length() > 2 && lastName.length() > 2) {
            for (Character i : firstName.toCharArray()) {
                if (!Character.isAlphabetic(i)) {
                    throw new IOException("В имени Инструктора нельзя вставлять цифры");
                }
            }
            for (Character i : lastName.toCharArray()) {
                if (!Character.isAlphabetic(i)) {
                    throw new IOException("В фамилию инструктора нелбзя вставлять цифры");
                }
            }
        } else {
            throw new IOException("В имени или в фамилии инструктора должно быть как минимум 3 буквы");
        }
        if (phoneNumber.length() == 13
                && phoneNumber.charAt(0) == '+'
                && phoneNumber.charAt(1) == '9'
                && phoneNumber.charAt(2) == '9'
                && phoneNumber.charAt(3) == '6') {
            int counter = 0;
            for (Character i : phoneNumber.toCharArray()) {
                if (counter != 0) {
                    if (!Character.isDefined(i)) {
                        throw new IOException("Формат номера не правильный");
                    }
                }
                counter++;
            }
        } else {
            throw new IOException("Формат номера не правильный");
        }

    }

    public Course getCourseId(Long id) {
        return courseRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Курс с такой id " + id + " не существует"));
    }

    public Instructor getInstructorId(Long id) {
        return instructorRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Инструктор с такой 'ID' " + id + " не существует"));
    }
}
