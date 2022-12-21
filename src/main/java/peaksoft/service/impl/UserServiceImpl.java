package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import peaksoft.dto.jwt.RegisterRequest;
import peaksoft.dto.jwt.RegisterResponse;
import peaksoft.entity.Role;
import peaksoft.entity.User;
import peaksoft.repository.RoleRepository;
import peaksoft.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    private User mapToEntity(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        return user;
    }

    private RegisterResponse mapToResponse(User user) {
        if (user == null) {
            return null;
        }
        RegisterResponse response = new RegisterResponse();
        if (user.getId() != null) {
            response.setId(String.valueOf(user.getId()));
        }
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        return response;
    }

    private List<RegisterResponse> mapToResponseList(List<User> users) {
        List<RegisterResponse> registerResponses = new ArrayList<>();
        for (User user : users) {
            registerResponses.add(mapToResponse(user));
        }

        return registerResponses;
    }


    @PostConstruct
    public void initMethod() {
        if (roleRepository.findAll().size() == 0 && roleRepository.findAll().size() == 0) {
            Role role1 = new Role();
            role1.setRoleName("Admin");

            Role role2 = new Role();
            role2.setRoleName("Instructor");

            Role role3 = new Role();
            role3.setRoleName("Student");

            RegisterRequest request = new RegisterRequest();
            request.setEmail("esen@gmail.com");
            request.setPassword(passwordEncoder.encode("1234"));
            request.setFirstName("Esen");

            User user2 = mapToEntity(request);

            user2.setRole(role1);
            role1.getUsers().add(user2);

            userRepository.save(user2);
            roleRepository.save(role1);
            roleRepository.save(role2);
            roleRepository.save(role3);
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).
                orElseThrow(() -> new UsernameNotFoundException("not found email"));
    }

    public List<RegisterResponse> getAllUsers() {
        return mapToResponseList(userRepository.findAll());
    }

    public RegisterResponse getUserById(Long id) {
        return mapToResponse(getUserId(id));
    }

    public RegisterResponse updateUser(Long id, RegisterRequest userRequest) throws IOException {

        User user = getUserId(id);
        if (user.getRole().getRoleName().equals("Instructor") ||
                user.getRole().getRoleName().equals("Student")) {
            throw new IOException("You can update instructor/student in another collention");
        }
        if (userRequest.getEmail() != null)
            user.setEmail(userRequest.getEmail());
        if (userRequest.getPassword() != null)
            user.setPassword(userRequest.getPassword());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRepository.save(user);
        return mapToResponse(user);
    }

    public RegisterResponse deleteUser(Long id) throws IOException {
        User user = getUserId(id);
        if (user.getRole().getRoleName().equals("Admin")) {
            throw new IOException("Вы не можете удалить администратора");
        }
        userRepository.delete(user);
        return mapToResponse(user);
    }

    public RegisterResponse changeRole(Long roleId, Long userId) throws IOException {
        User user = getUserId(userId);
        Role role = getRoleId(roleId);

        if (role.getRoleName().equals(("Admin"))) {
            throw new IOException("only 1 user can be admin");
        }

        if (user.getRole().getRoleName().equals(role.getRoleName())) {
            throw new IOException("this user already have this role");
        }

        user.setRole(role);
        role.getUsers().add(user);

        userRepository.save(user);
        roleRepository.save(role);

        return mapToResponse(user);
    }

    private User getUserId(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User с такой id " + id + " не существует"));
    }

    private Role getRoleId(Long id) {
        return roleRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role с такой id " + id + " не существует"));
    }

}
