package spotify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spotify.model.entity.AddListMusic;
import spotify.model.entity.ListMusic;
import spotify.model.entity.Song;
import spotify.model.entity.Users;

import java.util.List;

@Repository
public interface ListMusicRepository extends JpaRepository<ListMusic, Long> {
    boolean existsByAddListMusicIdAndSongIdAndAddListMusicId_UserId(AddListMusic addListMusicId, Song songId, Users users);

    List<ListMusic> getAllByAddListMusicId_UserId(Users users);

    boolean existsByListMusicIdAndAddListMusicId_UserId(Long id, Users users);
}
