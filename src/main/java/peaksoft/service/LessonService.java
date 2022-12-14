package peaksoft.service;

import peaksoft.dto.lesson.LessonRequest;
import peaksoft.dto.lesson.LessonResponse;
import peaksoft.entity.Lesson;


import java.util.List;

public interface LessonService {
    List<LessonResponse> getAllLessons(Long id);

    LessonResponse addLesson(Long id, LessonRequest lessonRequest);

    LessonResponse getLessonById(Long id);

    LessonResponse updateLesson(LessonRequest lessonRequest, Long id);

    LessonResponse deleteLesson(Long id);
}
