package peaksoft.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Setter
@Getter
@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    @SequenceGenerator(name = "role_seq", sequenceName = "role_seq", allocationSize = 1)
    private Long id;
    private String roleName;

    //  "  @ManyToMany(targetEntity = User.class, mappedBy = "roles", cascade = {DETACH, MERGE, REFRESH})
//    private List<User> users;
    @OneToMany(cascade = {MERGE, REFRESH, DETACH}, mappedBy = "role")
    private List<User> users = new ArrayList<>();

}
