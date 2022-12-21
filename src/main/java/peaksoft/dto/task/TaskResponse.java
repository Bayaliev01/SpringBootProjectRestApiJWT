package peaksoft.dto.task;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class TaskResponse {

    private Long id;
    private String taskName;

    private String taskText;

    private LocalDate deadLine;
}
