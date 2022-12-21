package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import peaksoft.dto.group.GroupRequest;
import peaksoft.dto.group.GroupsResponse;
import peaksoft.entity.*;
import peaksoft.mapper.group.GroupRequestConverts;
import peaksoft.mapper.group.GroupResponseConverts;
import peaksoft.repository.CompanyRepository;
import peaksoft.repository.CourseRepository;
import peaksoft.repository.GroupsRepository;
import peaksoft.repository.StudentRepository;
import peaksoft.service.GroupsService;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupsServiceImpl implements GroupsService {
    private final CourseRepository courseRepository;
    private final GroupsRepository groupsRepository;
    private final GroupRequestConverts groupRequestConverts;
    private final GroupResponseConverts groupResponseConverts;
    private final CompanyRepository companyRepository;
    private final StudentRepository studentRepository;

    @Override
    public List<GroupsResponse> getAllGroup() {
        return groupResponseConverts.view(groupsRepository.findAll());
    }

    @Override
    public List<GroupsResponse> getAllGroupByCompanyId(Long id) {
        Company company = getCompanyId(id);
        return groupResponseConverts.view(groupsRepository.findAllGroups(id));
    }

    @Override
    public List<GroupsResponse> getAllGroupsByCourseId(Long courseId) {
        Course course = getCourseId(courseId);
        return groupResponseConverts.view(course.getGroups());
    }

    @Override
    public GroupsResponse addGroupByCourseId(Long id, Long courseId, GroupRequest groupRequest) {
        Company company = getCompanyId(id);
        Course course = getCourseId(courseId);
        Groups groups = groupRequestConverts.createGroup(groupRequest);
        company.addGroup(groups);
        groups.setCompany(company);
        groups.addCourse(course);
        course.addGroup(groups);
        groupsRepository.save(groups);
        return groupResponseConverts.viewGroups(groups);
    }

    @Override
    public GroupsResponse addGroup(Long id, GroupRequest groupRequest) {
        Course course = getCourseId(id);
        Groups groups = groupRequestConverts.createGroup(groupRequest);
        course.addGroup(groups);
        groups.addCourse(course);
        groupsRepository.save(groups);
        return groupResponseConverts.viewGroups(groups);
    }

    @Override
    public GroupsResponse getGroupById(Long id) {
        Groups groups = getGroupsId(id);
        return groupResponseConverts.viewGroups(groups);
    }

    @Override
    public GroupsResponse updateGroup(GroupRequest groupRequest, Long id) {
        Groups groups = getGroupsId(id);
        groupRequestConverts.updateGroup(groups, groupRequest);
        return groupResponseConverts.viewGroups(groupsRepository.save(groups));
    }

    @Override
    public GroupsResponse deleteGroup(Long id) {
        Groups groups = getGroupsId(id);
        for (Student s : groups.getStudents()) {
            groups.getCompany().minusStudent();
        }
        for (Course c : groups.getCourses()) {
            for (Student student : groups.getStudents()) {
                for (Instructor i : c.getInstructors()) {
                    i.minus();
                }
            }
        }
        for (Course course : groups.getCourses()) {
            course.getGroups().remove(groups);
            groups.minusCount();
        }
        studentRepository.deleteAll(groups.getStudents());
        groups.setCourses(null);
        groupsRepository.delete(groups);
        return groupResponseConverts.viewGroups(groups);
    }

    @Override
    public GroupsResponse assignGroup(Long courseId, Long groupId) throws IOException {
        Groups group = getGroupsId(groupId);
        Course course = getCourseId(courseId);
        if (course.getGroups() != null) {
            for (Groups g : course.getGroups()) {
                if (g.getId() == groupId) {
                    throw new IOException("This group already exists!");
                }
            }
        }
        if (course.getInstructors()!= null){
            for (Instructor i: course.getInstructors()) {
                for (Student s:group.getStudents()) {
                    i.plus();
                }
            }
        }
        group.addCourse(course);
        course.addGroup(group);
        groupsRepository.save(group);
        courseRepository.save(course);
        return groupResponseConverts.viewGroups(group);
    }

    public Course getCourseId(Long id) {
        return courseRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Курс с такой id " + id + " не существует"));
    }

    public Company getCompanyId(Long id) {
        return companyRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Компания с такой id " + id + " не существует"));
    }

    public Groups getGroupsId(Long id) {
        return groupsRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Группа с такой id " + id + " не существует"));
    }
}
