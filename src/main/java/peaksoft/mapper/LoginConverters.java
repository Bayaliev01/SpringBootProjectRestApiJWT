package peaksoft.mapper;

import org.springframework.stereotype.Component;
import peaksoft.dto.jwt.LoginResponse;
import peaksoft.entity.Role;
import peaksoft.entity.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class LoginConverters {
    public LoginResponse loginView(String token,
                                   String message,
                                   User user){
        var loginResponse = new LoginResponse();
        if(user != null){
            setAuthorite(loginResponse,user.getRoles());
        }
        loginResponse.setJwtToken(token);
        loginResponse.setMessage(message);
        return loginResponse;
    }
    private void setAuthorite(LoginResponse loginResponse, List<Role> roles){
        Set<String> authorities = new HashSet<>();
        for(Role role : roles){
            authorities.add(role.getRoleName());
        }
        loginResponse.setAuthorities(authorities);
    }
}
