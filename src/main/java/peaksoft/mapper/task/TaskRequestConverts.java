package peaksoft.mapper.task;

import org.springframework.stereotype.Component;
import peaksoft.dto.task.TaskRequest;
import peaksoft.entity.Task;

@Component
public class TaskRequestConverts {

    public Task createTask(TaskRequest taskRequest) {
        if (taskRequest == null) {
            return null;
        }
        Task task = new Task();
        task.setTaskName(taskRequest.getTaskName());
        task.setTaskText(taskRequest.getTaskText());
        task.setDeadLine(taskRequest.getDeadLine());
        return task;
    }

    public void updateTask(Task task, TaskRequest taskRequest) {
        if (taskRequest.getTaskName() != null) {
            task.setTaskName(taskRequest.getTaskName());
        }
        if (taskRequest.getTaskText() != null) {
            task.setTaskText(taskRequest.getTaskText());
        }
        if (taskRequest.getDeadLine() != null) {
            task.setDeadLine(taskRequest.getDeadLine());
        }
    }
}
