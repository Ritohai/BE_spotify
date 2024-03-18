package spotify.service.listMusic;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import spotify.exception.customer.CustomerException;
import spotify.model.dto.ListMusicDTO;
import spotify.model.entity.ListMusic;

import java.io.IOException;
import java.util.List;

public interface IListMusicService {
    String createSong(ListMusicDTO listMusicDTO, Authentication authentication) throws CustomerException;

    List<ListMusic> getAll(Authentication authentication) throws CustomerException;

    String updateListSong(Authentication authentication, ListMusicDTO listMusicDTO, Long id) throws CustomerException;

    ListMusic findByIdList(Authentication authentication, Long id) throws CustomerException;

    String deleteListSong(Authentication authentication, Long id) throws CustomerException;

    String downloadsMusic(Authentication authentication, Long id) throws CustomerException, IOException;
}
