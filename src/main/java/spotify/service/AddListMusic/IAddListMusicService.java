package spotify.service.AddListMusic;

import org.springframework.security.core.Authentication;
import spotify.exception.customer.CustomerException;
import spotify.model.dto.AddListMusicDTO;
import spotify.model.entity.AddListMusic;

import java.util.List;

public interface IAddListMusicService {

    String createListMusic(Authentication authentication, AddListMusicDTO addListMusicDTO) throws CustomerException;

    List<AddListMusic> getAllList(Authentication authentication) throws CustomerException;

    String updateListMusic(Authentication authentication, AddListMusicDTO addListMusicDTO, Long id) throws CustomerException;

    AddListMusic findByIdMusic(Authentication authentication, Long id) throws CustomerException;

    String delete(Authentication authentication, Long id) throws CustomerException;

}
