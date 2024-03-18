package spotify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spotify.model.entity.Singer;

import java.util.List;

@Repository
public interface SingerRepository extends JpaRepository<Singer, Long> {
    boolean existsBySingerName(String singerName);

    List<Singer> findSingerBySingerNameAndSingerIdNot(String singerName, Long singerId);
}
