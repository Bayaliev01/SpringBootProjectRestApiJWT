package peaksoft.dto.task;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskResponse {

    private Long id;
    private String taskName;

    private String taskText;

    private String deadLine;
}
