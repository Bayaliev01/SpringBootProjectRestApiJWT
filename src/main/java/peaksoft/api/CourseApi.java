package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.course.CourseRequest;
import peaksoft.dto.course.CourseResponse;
import peaksoft.service.CourseService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseApi {
    private final CourseService courseService;


    @PostMapping("/{companyId}/saveCourse")
    @PreAuthorize("hasAuthority('Admin')")
    public CourseResponse createCourse(@PathVariable Long companyId, @RequestBody CourseRequest courseRequest) throws IOException {
        return courseService.addCourse(companyId, courseRequest);
    }

    @PutMapping("/{courseId}")
    @PreAuthorize("hasAuthority('Admin')")
    public CourseResponse updateCourse(@RequestBody CourseRequest courseRequest, @PathVariable Long courseId) {
        return courseService.updateCourse(courseRequest, courseId);
    }

    @GetMapping("/{courseId}")
    public CourseResponse getByIdCourse(@PathVariable Long courseId) {
        return courseService.getCourseById(courseId);
    }

    @DeleteMapping("/{courseId}")
    @PreAuthorize("hasAuthority('Admin')")
    public CourseResponse deleteCourse(@PathVariable Long courseId) {
        return courseService.deleteCourse(courseId);
    }


    @GetMapping("/{companyId}/allCourseById")
    public List<CourseResponse> getAllCourseById(@PathVariable Long companyId) {
        return courseService.getAllCoursesById(companyId);
    }

    @GetMapping("allCourse")
    public List<CourseResponse> getAllCourse() {
        return courseService.getAllCourses();
    }
}





