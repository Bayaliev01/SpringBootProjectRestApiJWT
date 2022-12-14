package peaksoft.mapper.course;

import org.springframework.stereotype.Component;
import peaksoft.dto.company.CompanyResponse;
import peaksoft.dto.course.CourseResponse;
import peaksoft.entity.Company;
import peaksoft.entity.Course;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseResponseConverts {
    public CourseResponse viewCourse(Course course) {
        if (course == null) {
            return null;
        }

        CourseResponse courseResponse = new CourseResponse();
        if (course.getId() != null) {
            courseResponse.setId(Long.valueOf(String.valueOf(course.getId())));
        }
        courseResponse.setId(course.getId());
        courseResponse.setCourseName(course.getCourseName());
        courseResponse.setDuration(course.getDuration());
        courseResponse.setDescription(course.getDescription());
        return courseResponse;
    }

    public List<CourseResponse> view(List<Course> courses) {
        List<CourseResponse> courseResponseList = new ArrayList<>();
        for (Course course : courses) {
            courseResponseList.add(viewCourse(course));
        }
        return courseResponseList;
    }
}
