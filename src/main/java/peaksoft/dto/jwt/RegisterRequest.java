package peaksoft.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class RegisterRequest {
    private String email;
    private String password;
    private String firstName;
}
