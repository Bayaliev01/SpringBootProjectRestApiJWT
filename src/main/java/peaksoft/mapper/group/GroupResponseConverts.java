package peaksoft.mapper.group;

import org.springframework.stereotype.Component;
import peaksoft.dto.company.CompanyResponse;
import peaksoft.dto.group.GroupsResponse;
import peaksoft.entity.Company;
import peaksoft.entity.Groups;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupResponseConverts {
    public GroupsResponse viewGroups(Groups groups) {
        if (groups == null) {
            return null;
        }
        GroupsResponse groupsResponse = new GroupsResponse();

        groupsResponse.setId(groups.getId());
        groupsResponse.setGroupName(groups.getGroupName());
        groupsResponse.setDateOfStart(groups.getDateOfStart());
        groupsResponse.setImage(groups.getImage());
        groupsResponse.setCount(groups.getCount());
        return groupsResponse;
    }

    public List<GroupsResponse> view(List<Groups> groups) {
        List<GroupsResponse> groupsResponseList = new ArrayList<>();
        for (Groups groups1 : groups) {
            groupsResponseList.add(viewGroups(groups1));
        }
        return groupsResponseList;
    }
}
