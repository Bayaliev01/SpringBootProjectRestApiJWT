package peaksoft.dto.student;


import lombok.Getter;
import lombok.Setter;
import peaksoft.enums.StudyFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Setter
@Getter
public class StudentResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @Enumerated(EnumType.STRING)
    private StudyFormat studyFormat;
}
