package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.group.GroupRequest;
import peaksoft.dto.group.GroupsResponse;
import peaksoft.service.GroupsService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupsApi {
    private final GroupsService groupsService;

    @PostMapping("/{courseId}/saveGroups")
    public GroupsResponse createGroups(@PathVariable Long courseId, @RequestBody GroupRequest groupRequest) {
        return groupsService.addGroup(courseId, groupRequest);
    }

    @PostMapping("/{companyId}/{courseId}/saveGroupsById")
    public GroupsResponse createGroupsById(@PathVariable Long companyId, @PathVariable Long courseId, @RequestBody GroupRequest groupRequest) {
        return groupsService.addGroupByCourseId(companyId, courseId, groupRequest);
    }

    @GetMapping("/{groupId}")
    public GroupsResponse getGroupsById(@PathVariable Long groupId) {
        return groupsService.getGroupById(groupId);
    }

    @PutMapping("/{groupsId}")
    public GroupsResponse updateGroups(@PathVariable Long groupsId, @RequestBody GroupRequest groupRequest) {
        return groupsService.updateGroup(groupRequest, groupsId);
    }

    @DeleteMapping("/{groupsId}")
    public GroupsResponse deleteGroups(@PathVariable Long groupsId) {
        return groupsService.deleteGroup(groupsId);
    }

    @GetMapping("getAllGroups")
    public List<GroupsResponse> getAllGroups() {
        return groupsService.getAllGroup();
    }

    @GetMapping("/{companyId}/getAllGroupsByCompanyId")
    public List<GroupsResponse> getAllGroupsByCompanyId(@PathVariable Long companyId) {
        return groupsService.getAllGroupByCompanyId(companyId);
    }

    @GetMapping("/{courseId}/getAllGroupsByCourseId")
    public List<GroupsResponse> getAllGroupsByCourseId(@PathVariable Long courseId) {
        return groupsService.getAllGroupsByCourseId(courseId);
    }

    @PostMapping("/{courseId}/{groupId}/assignCourseIdByGroupId")
    public GroupsResponse assignGroupByCourse(@PathVariable Long courseId, @PathVariable Long groupId) throws IOException {
        return groupsService.assignGroup(courseId, groupId);
    }
}
