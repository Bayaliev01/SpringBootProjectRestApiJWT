package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("select count(r) from Role r")
    Long countOfRoles();

    Role findByRoleName(String name);

}
