package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import peaksoft.dto.lesson.LessonRequest;
import peaksoft.dto.lesson.LessonResponse;
import peaksoft.entity.Course;
import peaksoft.entity.Lesson;
import peaksoft.mapper.lesson.LessonRequestConverts;
import peaksoft.mapper.lesson.LessonResponseConverts;
import peaksoft.repository.CourseRepository;
import peaksoft.repository.LessonRepository;
import peaksoft.service.LessonService;

import java.net.http.HttpHeaders;
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
        Course course = getCourseId(id);
        Lesson lesson = lessonRequestConverts.createLesson(lessonRequest);
        course.addLesson(lesson);
        lesson.setCourse(course);
        lessonRepository.save(lesson);
        return lessonResponseConverts.viewLesson(lesson);
    }

    @Override
    public LessonResponse getLessonById(Long id) {
        Lesson lesson = getLessonId(id);
        return lessonResponseConverts.viewLesson(lesson);
    }

    @Override
    public LessonResponse updateLesson(LessonRequest lessonRequest, Long id) {
        Lesson lesson = getLessonId(id);
        lessonRequestConverts.updateLesson(lesson, lessonRequest);
        lessonRepository.save(lesson);
        return lessonResponseConverts.viewLesson(lesson);
    }

    @Override
    public LessonResponse deleteLesson(Long id) {
        Lesson lesson = getLessonId(id);
        lessonRepository.delete(lesson);
        return lessonResponseConverts.viewLesson(lesson);
    }
    private Course getCourseId(Long id){
        return courseRepository.findById(id).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Курс с такой id "+id+" не существует"));
    }   private Lesson getLessonId(Long id){
        return lessonRepository.findById(id).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Lesson с такой id "+id+" не существует"));
    }
}

