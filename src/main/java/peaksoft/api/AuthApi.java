package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import peaksoft.config.ValidationExceptionType;
import peaksoft.config.jwt.JwtTokenUtil;
import peaksoft.dto.jwt.LoginResponse;
import peaksoft.dto.jwt.RegisterRequest;
import peaksoft.dto.jwt.RegisterResponse;
import peaksoft.entity.User;
import peaksoft.mapper.LoginConverters;
import peaksoft.repository.UserRepository;
import peaksoft.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jwt")
public class AuthApi {
    private final UserServiceImpl userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final LoginConverters loginConverter;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> getLogin(@RequestBody RegisterRequest request) {
        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(request.getEmail(),
                            request.getPassword());
            User user = userRepository.findByEmail(token.getName()).get();
            return ResponseEntity.ok().body(loginConverter.
                    loginView(jwtTokenUtil.generateToken(user),
                            ValidationExceptionType.SUCCESSFUL, user));

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                    body(loginConverter.
                            loginView("", ValidationExceptionType
                                    .LOGIN_FAILED, null));
        }
    }

    @PostMapping("/registration")
    public RegisterResponse create(@RequestBody RegisterRequest request) {
        return userService.create(request);
    }

    @GetMapping("/getAllUser")
    @PreAuthorize("isAuthenticated()")
    public List<RegisterResponse> getAllUser() {
        return userService.getAllUsers();
    }

    @GetMapping("/getUserById/{id}")
    @PreAuthorize("isAuthenticated()")
    public RegisterResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/updateUser/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public RegisterResponse updateUser(@PathVariable Long id, @RequestBody RegisterRequest registerRequest) {
        return userService.updateUser(id, registerRequest);
    }

    @DeleteMapping("/deleteUser/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public RegisterResponse deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @PostMapping("/changeRole/{roleId}/{userId}")
    public RegisterResponse changeRole(@PathVariable Long roleId, @PathVariable Long userId) throws IOException {
        return userService.changeRole(roleId, userId);
    }
}
