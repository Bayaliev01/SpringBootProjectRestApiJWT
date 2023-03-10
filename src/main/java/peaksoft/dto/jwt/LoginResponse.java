package peaksoft.dto.jwt;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class LoginResponse {
    private String jwtToken;
    private String message;
    private Set<String> authorities;
}
