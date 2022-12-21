package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import peaksoft.dto.course.CourseRequest;
import peaksoft.dto.course.CourseResponse;
import peaksoft.entity.Company;
import peaksoft.entity.Course;
import peaksoft.mapper.course.CourseRequestConverts;
import peaksoft.mapper.course.CourseResponseConverts;
import peaksoft.repository.CompanyRepository;
import peaksoft.repository.CourseRepository;
import peaksoft.service.CourseService;

import java.io.IOException;
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
        Company company = getCompanyId(companyId);
        return courseResponseConverts.view(repository.findAllCourses(companyId));
    }

    @Override
    public CourseResponse addCourse(Long companyId, CourseRequest courseRequest) throws IOException {
        validator(courseRequest.getCourseName().replace(" ", ""),
                courseRequest.getDescription().replace(" ", ""),
                courseRequest.getDuration());
        Company company = getCompanyId(companyId);
        Course course = courseRequestConverts.createCourse(courseRequest);
        company.addCourse(course);
        course.setCompany(company);
        repository.save(course);
        return courseResponseConverts.viewCourse(course);
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        Course course = getCourseId(id);
        return courseResponseConverts.viewCourse(course);
    }

    @Override
    public CourseResponse updateCourse(CourseRequest courseRequest, Long id) {
        Course course = getCourseId(id);
        courseRequestConverts.updateCourse(course, courseRequest);
        repository.save(course);
        return courseResponseConverts.viewCourse(course);
    }

    @Override
    public CourseResponse deleteCourse(Long id) {
        Course course = getCourseId(id);
        course.setCompany(null);
        repository.delete(course);
        return courseResponseConverts.viewCourse(course);
    }

    private void validator(String courseName, String description, int duration) throws IOException {
        if (courseName.length() > 3 && description.length() > 5 && duration > 2 && duration < 12) {
            for (Character i : courseName.toCharArray()) {
                if (!Character.isLetter(i)) {
                    throw new IOException("В названии курса нельзя вставлять цифры");
                }
            }
        } else {
            throw new IOException("Form error course registration");
        }
    }

    public Course getCourseId(Long id) {

        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Курс с такой id " + id + " не существует"));
    }

    public Company getCompanyId(Long id) {
        return companyRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Компания с такой id " + id + " не существует"));
    }
}
