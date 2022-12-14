package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.dto.lesson.LessonRequest;
import peaksoft.dto.lesson.LessonResponse;
import peaksoft.entity.Course;
import peaksoft.entity.Lesson;
import peaksoft.mapper.lesson.LessonRequestConverts;
import peaksoft.mapper.lesson.LessonResponseConverts;
import peaksoft.repository.CourseRepository;
import peaksoft.repository.LessonRepository;
import peaksoft.service.LessonService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final LessonResponseConverts lessonResponseConverts;
    private final LessonRequestConverts lessonRequestConverts;

    @Override
    public List<LessonResponse> getAllLessons(Long id) {
        return lessonResponseConverts.view(lessonRepository.findAllLessonById(id));
    }

    @Override
    public LessonResponse addLesson(Long id, LessonRequest lessonRequest) {
        Course course = courseRepository.findById(id).get();
        Lesson lesson = lessonRequestConverts.createLesson(lessonRequest);
        course.addLesson(lesson);
        lesson.setCourse(course);
        lessonRepository.save(lesson);
        return lessonResponseConverts.viewLesson(lesson);
    }

    @Override
    public LessonResponse getLessonById(Long id) {
        Lesson lesson = lessonRepository.findById(id).get();
        return lessonResponseConverts.viewLesson(lesson);
    }

    @Override
    public LessonResponse updateLesson(LessonRequest lessonRequest, Long id) {
        Lesson lesson = lessonRepository.findById(id).get();
        lessonRequestConverts.updateLesson(lesson, lessonRequest);
        lessonRepository.save(lesson);
        return lessonResponseConverts.viewLesson(lesson);
    }

    @Override
    public LessonResponse deleteLesson(Long id) {
        Lesson lesson = lessonRepository.findById(id).get();
        lessonRepository.delete(lesson);
        return lessonResponseConverts.viewLesson(lesson);
    }
}

