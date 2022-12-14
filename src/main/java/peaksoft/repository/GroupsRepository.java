package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Groups;


import java.util.List;

@Repository
public interface GroupsRepository extends JpaRepository<Groups, Long> {

    @Query(value = "select * from groups  where groups.company_id= :companyId", nativeQuery = true)
    List<Groups> findAllGroups(Long companyId);
}
