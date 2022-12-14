package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.lesson.LessonRequest;
import peaksoft.dto.lesson.LessonResponse;
import peaksoft.service.LessonService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lesson")
public class LessonApi {
    private final LessonService lessonService;


    @PostMapping("/{courseId}/saveLesson")
    public LessonResponse createLesson(@PathVariable Long courseId, @RequestBody LessonRequest lessonRequest) {
        return lessonService.addLesson(courseId, lessonRequest);
    }

    @PutMapping("/{lessonId}")
    public LessonResponse updateLesson(@PathVariable Long lessonId, @RequestBody LessonRequest lessonRequest) {
        return lessonService.updateLesson(lessonRequest, lessonId);
    }

    @GetMapping("/{lessonId}")
    public LessonResponse getLessonById(@PathVariable Long lessonId) {
        return lessonService.getLessonById(lessonId);
    }

    @DeleteMapping("/{lessonId}")
    public LessonResponse deleteLesson(@PathVariable Long lessonId) {
        return lessonService.deleteLesson(lessonId);
    }

    @GetMapping("/{courseId}/getAllLessonByCourseId")
    public List<LessonResponse> getAllLesson(@PathVariable Long courseId) {
        return lessonService.getAllLessons(courseId);
    }
}
