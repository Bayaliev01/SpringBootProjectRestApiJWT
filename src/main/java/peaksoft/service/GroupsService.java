package peaksoft.service;

import peaksoft.dto.group.GroupRequest;
import peaksoft.dto.group.GroupsResponse;
import peaksoft.entity.Groups;


import java.io.IOException;
import java.util.List;

public interface GroupsService {
    List<GroupsResponse> getAllGroup();

    List<GroupsResponse> getAllGroupByCompanyId(Long id);

    List<GroupsResponse> getAllGroupsByCourseId(Long courseId);

    GroupsResponse addGroupByCourseId(Long id, Long courseId, GroupRequest groupRequest);

    GroupsResponse addGroup(Long id, GroupRequest groupRequest);

    GroupsResponse getGroupById(Long id);

    GroupsResponse updateGroup(GroupRequest groupRequest, Long id);

    GroupsResponse deleteGroup(Long id);

    GroupsResponse assignGroup(Long courseId, Long groupId) throws IOException;
}
