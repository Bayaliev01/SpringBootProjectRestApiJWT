package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.dto.student.StudentRequest;
import peaksoft.dto.student.StudentResponse;
import peaksoft.entity.Course;
import peaksoft.entity.Groups;
import peaksoft.entity.Instructor;
import peaksoft.entity.Student;
import peaksoft.mapper.student.StudentRequestConverts;
import peaksoft.mapper.student.StudentResponseConverts;
import peaksoft.repository.GroupsRepository;
import peaksoft.repository.StudentRepository;
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

    @Override
    public List<StudentResponse> getAllStudents(Long id) {
        return studentResponseConverts.view(studentRepository.findAllStudentById(id));
    }

    @Override
    public List<StudentResponse> listAllStudents() {
        return studentResponseConverts.view(studentRepository.findAllStudent());
    }

    @Override
    public StudentResponse addStudent(Long id, StudentRequest studentRequest) {
        Groups group = groupsRepository.findById(id).get();
        Student student = studentRequestConverts.createStudent(studentRequest);
        group.addStudent(student);
        student.setGroups(group);
        for (Course c : student.getGroups().getCompany().getCourses()) {
            for (Instructor i : c.getInstructors()) {
                i.plus();
            }
        }
        studentRepository.save(student);
        return studentResponseConverts.viewStudent(student);
    }

    @Override
    public StudentResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id).get();
        return studentResponseConverts.viewStudent(student);
    }

    @Override
    public StudentResponse updateStudent(StudentRequest studentRequest, Long id) {
        Student student = studentRepository.findById(id).get();
        studentRequestConverts.updateStudent(student, studentRequest);
        studentRepository.save(student);
        return studentResponseConverts.viewStudent(student);
    }

    @Override
    public StudentResponse deleteStudent(Long id) {
        Student student = studentRepository.findById(id).get();
        student.getGroups().getCompany().minusStudent();
        for (Course c:student.getGroups().getCourses()) {
            for (Instructor i:c.getInstructors()) {
                i.minus();
            }
        }
        student.setGroups(null);
        studentRepository.delete(student);
        return studentResponseConverts.viewStudent(student);
    }

    @Override
    public StudentResponse assignStudent(Long groupId, Long studentId) throws IOException {
        Groups group = groupsRepository.findById(groupId).get();
        Student student = studentRepository.findById(studentId).get();

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
}
