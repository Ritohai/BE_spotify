package spotify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spotify.model.entity.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
}
