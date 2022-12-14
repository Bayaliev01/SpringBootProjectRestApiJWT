package peaksoft.dto.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Setter
@Getter

public class GroupsResponse {
    private Long id;
    private String groupName;
    @CreatedDate
    @CreationTimestamp
    private LocalDateTime dateOfStart;
    private String image;
    private int count;
}
