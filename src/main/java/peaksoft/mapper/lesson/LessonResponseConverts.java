package peaksoft.mapper.lesson;

import org.springframework.stereotype.Component;
import peaksoft.dto.lesson.LessonResponse;
import peaksoft.entity.Lesson;

import java.util.ArrayList;
import java.util.List;

@Component
public class LessonResponseConverts {

    public LessonResponse viewLesson(Lesson lesson) {
        if (lesson == null) {
            return null;
        }
        LessonResponse lessonResponse = new LessonResponse();
        lessonResponse.setId(lesson.getId());
        lessonResponse.setLessonName(lesson.getLessonName());
        return lessonResponse;
    }

    public List<LessonResponse> view(List<Lesson> lessons) {
        List<LessonResponse> lessonResponseList = new ArrayList<>();
        for (Lesson lesson : lessons) {
            lessonResponseList.add(viewLesson(lesson));
        }
        return lessonResponseList;
    }
}
