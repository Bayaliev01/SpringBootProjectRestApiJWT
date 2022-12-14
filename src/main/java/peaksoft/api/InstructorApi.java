package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.instructor.InstructorRequest;
import peaksoft.dto.instructor.InstructorResponse;
import peaksoft.service.InstructorService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/instructor")
public class InstructorApi {
    private final InstructorService instructorService;

    @PostMapping("/{courseId}/saveInstructor")
    public InstructorResponse createInstructor(@PathVariable Long courseId, @RequestBody InstructorRequest instructorRequest) {
        return instructorService.addInstructor(courseId, instructorRequest);
    }

    @GetMapping("/{instructorId}")
    public InstructorResponse getInstructorById(@PathVariable Long instructorId) {
        return instructorService.getInstructorById(instructorId);
    }

    @PutMapping("/{instructorId}")
    public InstructorResponse updateInstructor(@PathVariable Long instructorId, @RequestBody InstructorRequest instructorRequest) throws IOException {
        return instructorService.updateInstructor(instructorRequest, instructorId);
    }

    @DeleteMapping("/{instructorId}")
    public InstructorResponse deleteInstructor(@PathVariable Long instructorId){
        return instructorService.deleteInstructor(instructorId);
    }

    @GetMapping("allInstructor")
    public List<InstructorResponse> getAllInstructor(){
        return instructorService.listAllInstructors();
    }

    @GetMapping("/{courseId}/allInstructorByCourseId")
    public List<InstructorResponse> getAllInstructorByCourseId(@PathVariable Long courseId){
        return instructorService.getAllInstructor(courseId);
    }

    @PostMapping("/{courseId}/{instructorId}")
    public InstructorResponse assignInstructorByCourseId(@PathVariable Long courseId, @PathVariable Long instructorId) throws IOException {
        return instructorService.assignInstructor(courseId,instructorId);
    }
}




