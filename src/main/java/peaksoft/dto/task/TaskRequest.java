package peaksoft.dto.task;



import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskRequest {
    private String taskName;

    private String taskText;

    private String deadLine;
}
