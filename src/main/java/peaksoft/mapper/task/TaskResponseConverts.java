package peaksoft.mapper.task;

import org.springframework.stereotype.Component;
import peaksoft.dto.task.TaskResponse;
import peaksoft.entity.Task;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskResponseConverts {

    public TaskResponse viewTask(Task task) {
        if (task == null) {
            return null;
        }
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setTaskName(task.getTaskName());
        taskResponse.setTaskText(task.getTaskText());
        taskResponse.setDeadLine(task.getDeadLine());
        return taskResponse;
    }

    public List<TaskResponse> view(List<Task> tasks) {
        List<TaskResponse> taskResponseList = new ArrayList<>();
        for (Task task : tasks) {
            taskResponseList.add(viewTask(task));
        }
        return taskResponseList;
    }
}
