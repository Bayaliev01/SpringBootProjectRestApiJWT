package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
        return groupResponseConverts.view(groupsRepository.findAllGroups(id));
    }

    @Override
    public List<GroupsResponse> getAllGroupsByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId).get();
        return groupResponseConverts.view(course.getGroups());
    }

    @Override
    public GroupsResponse addGroupByCourseId(Long id, Long courseId, GroupRequest groupRequest) {
        Company company = companyRepository.findById(id).get();
        Course course = courseRepository.findById(courseId).get();
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
        Course course = courseRepository.findById(id).get();
        Groups groups = groupRequestConverts.createGroup(groupRequest);
        course.addGroup(groups);
        groups.addCourse(course);
        groupsRepository.save(groups);
        return groupResponseConverts.viewGroups(groups);
    }

    @Override
    public GroupsResponse getGroupById(Long id) {
        return groupResponseConverts.viewGroups(groupsRepository.findById(id).get());
    }

    @Override
    public GroupsResponse updateGroup(GroupRequest groupRequest, Long id) {
        Groups groups = groupsRepository.findById(id).get();
        groupRequestConverts.updateGroup(groups, groupRequest);
        return groupResponseConverts.viewGroups(groupsRepository.save(groups));
    }

    @Override
    public GroupsResponse deleteGroup(Long id) {
        Groups groups = groupsRepository.findById(id).get();
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
        Groups group = groupsRepository.findById(groupId).get();
        Course course = courseRepository.findById(courseId).get();
        if (course.getGroups() != null) {
            for (Groups g : course.getGroups()) {
                if (g.getId() == groupId) {
                    throw new IOException("This group already exists!");
                }
            }
        }
        group.addCourse(course);
        course.addGroup(group);
        groupsRepository.save(group);
        courseRepository.save(course);
        return groupResponseConverts.viewGroups(group);
    }

}
