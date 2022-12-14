package peaksoft.mapper.course;

import org.springframework.stereotype.Component;
import peaksoft.dto.company.CompanyRequest;
import peaksoft.dto.course.CourseRequest;
import peaksoft.entity.Company;
import peaksoft.entity.Course;

@Component
public class CourseRequestConverts {
    public Course createCourse(CourseRequest courseRequest) {
        if (courseRequest == null) {
            return null;
        }
        Course course = new Course();
        course.setCourseName(courseRequest.getCourseName());
        course.setDuration(courseRequest.getDuration());
        course.setDescription(courseRequest.getDescription());
        return course;
    }

    public void updateCourse(Course course, CourseRequest courseRequest) {
        course.setCourseName(courseRequest.getCourseName());
        course.setDuration(courseRequest.getDuration());
        course.setDescription(courseRequest.getDescription());
    }
}
