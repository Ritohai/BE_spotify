package spotify.service.song;

import jakarta.servlet.http.HttpServletResponse;
import spotify.exception.customer.CustomerException;
import spotify.model.dto.SongDTO;
import spotify.model.entity.Song;

import java.io.IOException;
import java.util.List;

public interface ISongService {
    String createSong(SongDTO songDTO) throws CustomerException, IOException;

    String updateSong(SongDTO songDTO, Long id) throws CustomerException;

    Song findById(Long id) throws CustomerException;

    List<Song> getAll();

    String changStatus(Long id) throws CustomerException;

    void exportCSV(HttpServletResponse response,List<Song> list) throws IOException;

}
