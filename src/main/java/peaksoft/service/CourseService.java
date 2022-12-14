package peaksoft.service;

import peaksoft.dto.company.CompanyResponse;
import peaksoft.dto.course.CourseRequest;
import peaksoft.dto.course.CourseResponse;
import peaksoft.entity.Course;


import java.util.List;

public interface CourseService {
    List<CourseResponse> getAllCourses();

    List<CourseResponse> getAllCoursesById(Long companyId);

    CourseResponse addCourse(Long companyId, CourseRequest courseRequest);

    CourseResponse getCourseById(Long id);

    CourseResponse updateCourse(CourseRequest courseRequest, Long id);

    CourseResponse deleteCourse(Long id);
}
