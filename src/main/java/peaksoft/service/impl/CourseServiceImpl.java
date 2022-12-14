package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.dto.course.CourseRequest;
import peaksoft.dto.course.CourseResponse;
import peaksoft.entity.Company;
import peaksoft.entity.Course;
import peaksoft.mapper.course.CourseRequestConverts;
import peaksoft.mapper.course.CourseResponseConverts;
import peaksoft.repository.CompanyRepository;
import peaksoft.repository.CourseRepository;
import peaksoft.service.CourseService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository repository;
    private final CompanyRepository companyRepository;
    private final CourseResponseConverts courseResponseConverts;
    private final CourseRequestConverts courseRequestConverts;


    @Override
    public List<CourseResponse> getAllCourses() {
        return courseResponseConverts.view(repository.findAll());
    }

    @Override
    public List<CourseResponse> getAllCoursesById(Long companyId) {
        return courseResponseConverts.view(repository.findAllCourses(companyId));
    }

    @Override
    public CourseResponse addCourse(Long companyId, CourseRequest courseRequest) {
        Company company = companyRepository.findById(companyId).get();
        Course course = courseRequestConverts.createCourse(courseRequest);
        company.addCourse(course);
        course.setCompany(company);
        repository.save(course);
        return courseResponseConverts.viewCourse(course);
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        Course course = repository.findById(id).get();
        return courseResponseConverts.viewCourse(course);
    }

    @Override
    public CourseResponse updateCourse(CourseRequest courseRequest, Long id) {
        Course course = repository.findById(id).get();
        courseRequestConverts.updateCourse(course, courseRequest);
        repository.save(course);
        return courseResponseConverts.viewCourse(course);
    }

    @Override
    public CourseResponse deleteCourse(Long id) {
        Course course = repository.findById(id).get();
        course.setCompany(null);
        repository.delete(course);
        return courseResponseConverts.viewCourse(course);
    }
}
