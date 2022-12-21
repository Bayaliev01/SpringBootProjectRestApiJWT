package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.webjars.NotFoundException;
import peaksoft.dto.student.StudentRequest;
import peaksoft.dto.student.StudentResponse;
import peaksoft.entity.*;
import peaksoft.mapper.student.StudentRequestConverts;
import peaksoft.mapper.student.StudentResponseConverts;
import peaksoft.repository.GroupsRepository;
import peaksoft.repository.RoleRepository;
import peaksoft.repository.StudentRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.StudentService;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final GroupsRepository groupsRepository;
    private final StudentRequestConverts studentRequestConverts;
    private final StudentResponseConverts studentResponseConverts;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public List<StudentResponse> getAllStudents(Long id) {
        return studentResponseConverts.view(studentRepository.findAllStudentById(id));
    }

    @Override
    public List<StudentResponse> listAllStudents() {
        return studentResponseConverts.view(studentRepository.findAllStudent());
    }

    @Override
    public StudentResponse addStudent(Long id, StudentRequest studentRequest) throws IOException {

        List<Student> students = studentRepository.findAll();
        Student student = studentRequestConverts.createStudent(studentRequest);
        validator(student.getPhoneNumber().replace(" ", ""),
                student.getFirstName().replace(" ", ""),
                student.getLastName().replace(" ", ""));
        for (Student i : students) {
            if (i.getEmail().equals(studentRequest.getEmail())) {
                throw new IOException("Student with email already exists!");
            }
        }

        Groups group =getGroupId(id);
        group.addStudent(student);
        student.setGroups(group);
        for (Course c:student.getGroups().getCourses()) {
            for (Instructor i: c.getInstructors()) {
                i.plus();
            }
        }

        User user = new User();
        Role role = roleRepository.findById(3L).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role с такой id " + id + " не существует"));
        user.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
        user.setFirstName(studentRequest.getFirstName());
        user.setEmail(studentRequest.getEmail());
        user.setRole(role);
        role.getUsers().add(user);
        student.setUser(user);

        studentRepository.save(student);
        userRepository.save(user);
        roleRepository.save(role);
        return studentResponseConverts.viewStudent(student);
    }


    @Override
    public StudentResponse getStudentById(Long id) {
        Student student = getStudentId(id);
        return studentResponseConverts.viewStudent(student);
    }

    @Override
    public StudentResponse updateStudent(StudentRequest studentRequest, Long id) throws IOException {
        validator(studentRequest.getPhoneNumber().replace(" ", ""),
                studentRequest.getFirstName().replace(" ", ""),
                studentRequest.getLastName().replace(" ", ""));
        Student student = getStudentId(id);
        User user = student.getUser();
        if (studentRequest.getEmail() != null)
            user.setEmail(studentRequest.getEmail());
        if (studentRequest.getPassword() != null)
            user.setPassword(studentRequest.getPassword());
        if (studentRequest.getFirstName() != null)
            user.setFirstName(studentRequest.getFirstName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        studentRequestConverts.updateStudent(student, studentRequest);
        studentRepository.save(student);
        userRepository.save(user);
        return studentResponseConverts.viewStudent(student);
    }

    @Override
    public StudentResponse deleteStudent(Long id) {
        Student student = getStudentId(id);
        student.getGroups().getCompany().minusStudent();
        for (Course c : student.getGroups().getCourses()) {
            for (Instructor i : c.getInstructors()) {
                i.minus();
            }
        }
        student.setGroups(null);
        Role role = student.getUser().getRole();
        role.getUsers().remove(student.getUser());
        userRepository.delete(student.getUser());
        studentRepository.delete(student);
        return studentResponseConverts.viewStudent(student);
    }

    @Override
    public StudentResponse assignStudent(Long groupId, Long studentId) throws IOException {
        Groups group = getGroupId(groupId);
        Student student = getStudentId(studentId);

        if (group.getStudents() != null) {
            for (Student g : group.getStudents()) {
                if (g.getId() == studentId) {
                    throw new IOException("This student already exists!");
                }
            }
        }
        for (Course c : student.getGroups().getCourses()) {
            for (Instructor i : c.getInstructors()) {
                i.minus();
            }
        }
        for (Course c : group.getCourses()) {
            for (Instructor i : c.getInstructors()) {
                i.plus();
            }
        }
        student.getGroups().getStudents().remove(student);
        group.assignStudent(student);
        student.setGroups(group);
        studentRepository.save(student);
        groupsRepository.save(group);
        return studentResponseConverts.viewStudent(student);
    }

    private void validator(String phone, String firstName, String lastName) throws IOException {
        if (firstName.length() > 2 && lastName.length() > 2) {
            for (Character i : firstName.toCharArray()) {
                if (!Character.isAlphabetic(i)) {
                    throw new IOException("В имени студента нельзя вставлять цифры");
                }
            }

            for (Character i : lastName.toCharArray()) {
                if (!Character.isAlphabetic(i)) {
                    throw new IOException("В фамилию студента нельзя вставлять цифры");
                }
            }
        } else {
            throw new IOException("В имени или фамилии студента должно быть как минимум 3 буквы");
        }

        if (phone.length() == 13
                && phone.charAt(0) == '+'
                && phone.charAt(1) == '9'
                && phone.charAt(2) == '9'
                && phone.charAt(3) == '6') {
            int counter = 0;

            for (Character i : phone.toCharArray()) {
                if (counter != 0) {
                    if (!Character.isDigit(i)) {
                        throw new IOException("Формат номера не правильный");
                    }
                }
                counter++;
            }
        } else {
            throw new IOException("Формат номера не правильный");
        }
    }

    private Student getStudentId(Long id) {
        return studentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Студент с такой id " + id + " не существует"));
    }

    private Groups getGroupId(Long id) {
        return groupsRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Группа с такой id " + id + " не существует"));
    }
    private Role getRoleId(Long id) {
        return roleRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role с такой id " + id + " не существует"));
    }
}
