package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.threads.TaskThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import peaksoft.dto.task.TaskRequest;
import peaksoft.dto.task.TaskResponse;
import peaksoft.entity.Lesson;
import peaksoft.entity.Task;
import peaksoft.mapper.task.TaskRequestConverts;
import peaksoft.mapper.task.TaskResponseConverts;
import peaksoft.repository.LessonRepository;
import peaksoft.repository.TaskRepository;
import peaksoft.service.TaskService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final LessonRepository lessonRepository;
    private final TaskRequestConverts taskRequestConverts;
    private final TaskResponseConverts taskResponseConverts;

    @Override
    public List<TaskResponse> getAllTasks(Long id) {
        return taskResponseConverts.view(taskRepository.findAllTaskById(id));
    }

    @Override
    public TaskResponse addTask(Long id, TaskRequest taskRequest) {
        Lesson lesson = getLessonId(id);
        Task task = taskRequestConverts.createTask(taskRequest);
        lesson.addTask(task);
        task.setLesson(lesson);
        taskRepository.save(task);
        return taskResponseConverts.viewTask(task);
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        Task task = getTaskId(id);
        return taskResponseConverts.viewTask(task);
    }

    @Override
    public TaskResponse updateTask(TaskRequest taskRequest, Long id) {
        Task task = getTaskId(id);
        taskRequestConverts.updateTask(task, taskRequest);
        taskRepository.save(task);
        return taskResponseConverts.viewTask(task);
    }

    @Override
    public TaskResponse deleteTask(Long id) {
        Task task = getTaskId(id);
        taskRepository.delete(task);
        return taskResponseConverts.viewTask(task);
    }

    private Lesson getLessonId(Long id) {
        return lessonRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson с такой id " + id + " не существует"));
    }

    private Task getTaskId(Long id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task с такой id " + id + " не существует"));
    }
}
