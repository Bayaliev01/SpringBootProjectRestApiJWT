package peaksoft.dto.jwt;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterResponse {

    private String id;
    private String email;
    private String firstName;
}
