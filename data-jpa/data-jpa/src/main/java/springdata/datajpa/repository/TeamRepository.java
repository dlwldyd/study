package springdata.datajpa.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import springdata.datajpa.entity.Team;

import javax.persistence.LockModeType;
import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @EntityGraph(attributePaths = {"members"})
    @Lock(LockModeType.OPTIMISTIC)
    List<Team> findLockByName(String name);
}
