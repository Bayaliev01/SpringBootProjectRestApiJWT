package peaksoft.mapper.group;

import org.springframework.stereotype.Component;
import peaksoft.dto.course.CourseRequest;
import peaksoft.dto.group.GroupRequest;
import peaksoft.entity.Course;
import peaksoft.entity.Groups;

@Component
public class GroupRequestConverts {

    public Groups createGroup(GroupRequest groupRequest) {
        if (groupRequest == null) {
            return null;
        }
        Groups groups = new Groups();
        groups.setGroupName(groupRequest.getGroupName());
        groups.setImage(groupRequest.getImage());
        return groups;
    }

    public void updateGroup(Groups groups, GroupRequest groupRequest) {
        groups.setGroupName(groupRequest.getGroupName());
        groups.setImage(groupRequest.getImage());
    }
}
