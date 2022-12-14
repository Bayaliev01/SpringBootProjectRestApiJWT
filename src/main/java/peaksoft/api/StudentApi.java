package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.student.StudentRequest;
import peaksoft.dto.student.StudentResponse;
import peaksoft.service.StudentService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/student")
public class StudentApi {
    private final StudentService studentService;

    @PostMapping("/{groupId}/saveStudent")
    public StudentResponse createStudent(@PathVariable Long groupId, @RequestBody StudentRequest studentRequest) {
        return studentService.addStudent(groupId, studentRequest);
    }

    @PutMapping("/{studentId}")
    public StudentResponse updateStudent(@PathVariable Long studentId, @RequestBody StudentRequest studentRequest) {
        return studentService.updateStudent(studentRequest, studentId);
    }

    @GetMapping("/{studentId}")
    public StudentResponse getStudentById(@PathVariable Long studentId) {
        return studentService.getStudentById(studentId);
    }

    @DeleteMapping("/{studentId}")
    public StudentResponse deleteStudent(@PathVariable Long studentId) {
        return studentService.deleteStudent(studentId);
    }

    @GetMapping("getAll")
    public List<StudentResponse> getAllStudent() {
        return studentService.listAllStudents();
    }

    @GetMapping("/{groupId}/getAllStudentByGroupId")
    public List<StudentResponse> getAllStudentByGroupId(@PathVariable Long groupId) {
        return studentService.getAllStudents(groupId);
    }

    @PostMapping("/{groupId}/{studentId}")
    public StudentResponse assignStudent(@PathVariable Long groupId, @PathVariable Long studentId) throws IOException {
        return studentService.assignStudent(groupId, studentId);
    }
}
