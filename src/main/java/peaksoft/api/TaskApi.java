package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.task.TaskRequest;
import peaksoft.dto.task.TaskResponse;
import peaksoft.service.TaskService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/task")
public class TaskApi {
    private final TaskService taskService;


    @PostMapping("/{lessonId}/saveTask")
    public TaskResponse createTask(@PathVariable Long lessonId, @RequestBody TaskRequest taskRequest) {
        return taskService.addTask(lessonId, taskRequest);
    }

    @PutMapping("/{taskId}")
    public TaskResponse updateTask(@PathVariable Long taskId, @RequestBody TaskRequest taskRequest) {
        return taskService.updateTask(taskRequest, taskId);
    }

    @DeleteMapping("/{taskId}")
    public TaskResponse deleteTask(@PathVariable Long taskId) {
        return taskService.deleteTask(taskId);
    }

    @GetMapping("/{taskId}")
    public TaskResponse getTaskById(@PathVariable Long taskId) {
        return taskService.getTaskById(taskId);
    }

    @GetMapping("/{lessonId}/getAllTaskByLessonId")
    public List<TaskResponse> getAllTask(@PathVariable Long lessonId) {
        return taskService.getAllTasks(lessonId);
    }
}
