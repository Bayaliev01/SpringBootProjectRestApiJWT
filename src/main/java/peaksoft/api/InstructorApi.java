package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('Admin')")
    public InstructorResponse createInstructor(@PathVariable Long courseId, @RequestBody InstructorRequest instructorRequest) throws IOException {
        return instructorService.addInstructor(courseId, instructorRequest);
    }

    @GetMapping("/{instructorId}")
    public InstructorResponse getInstructorById(@PathVariable Long instructorId) {
        return instructorService.getInstructorById(instructorId);
    }

    @PutMapping("/{instructorId}")
    @PreAuthorize("hasAnyAuthority('Admin')")
    public InstructorResponse updateInstructor(@PathVariable Long instructorId, @RequestBody InstructorRequest instructorRequest) throws IOException {
        return instructorService.updateInstructor(instructorRequest, instructorId);
    }

    @DeleteMapping("/{instructorId}")
    @PreAuthorize("hasAnyAuthority('Admin')")
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
    @PreAuthorize("hasAnyAuthority('Admin')")
    public InstructorResponse assignInstructorByCourseId(@PathVariable Long courseId,
                                                         @PathVariable Long instructorId) throws IOException {
        return instructorService.assignInstructor(courseId,instructorId);
    }
}




