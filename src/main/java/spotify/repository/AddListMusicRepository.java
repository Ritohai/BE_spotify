package spotify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spotify.model.entity.AddListMusic;
import spotify.model.entity.Users;

import java.util.List;

@Repository
public interface AddListMusicRepository extends JpaRepository<AddListMusic, Long> {

    List<AddListMusic> getAllByUserId(Users users);

    boolean existsByNameListAndUserId(String nameList, Users users);

    boolean existsByListIdAndUserId(Long id, Users users);
}
