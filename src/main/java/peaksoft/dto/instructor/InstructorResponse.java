package peaksoft.dto.instructor;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InstructorResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private int students = 0;
}
