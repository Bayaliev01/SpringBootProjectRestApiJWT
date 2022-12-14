package peaksoft.mapper.lesson;

import org.springframework.stereotype.Component;
import peaksoft.dto.lesson.LessonRequest;
import peaksoft.entity.Lesson;

@Component
public class LessonRequestConverts {

    public Lesson createLesson(LessonRequest lessonRequest) {
        if (lessonRequest == null) {
            return null;
        }
        Lesson lesson = new Lesson();
        lesson.setLessonName(lessonRequest.getLessonName());
        return lesson;
    }

    public void updateLesson(Lesson lesson, LessonRequest lessonRequest) {
        if (lessonRequest.getLessonName() != null) {
            lesson.setLessonName(lessonRequest.getLessonName());
        }
    }
}
