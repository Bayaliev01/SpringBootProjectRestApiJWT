package peaksoft.service;

import peaksoft.dto.task.TaskRequest;
import peaksoft.dto.task.TaskResponse;
import peaksoft.entity.Task;


import java.util.List;

public interface TaskService {
    List<TaskResponse> getAllTasks(Long id);

    TaskResponse addTask(Long id, TaskRequest taskRequest);

    TaskResponse getTaskById(Long id);

    TaskResponse updateTask(TaskRequest taskRequest, Long id);

    TaskResponse deleteTask(Long id);
}
