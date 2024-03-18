package spotify.service.singer;

import spotify.exception.customer.CustomerException;
import spotify.model.dto.SingerDTO;
import spotify.model.entity.Singer;

import java.util.List;

public interface ISingerService {
    String createSinger(SingerDTO singerDTO) throws CustomerException;

    String updateSinger(Long id, SingerDTO singerDTO) throws CustomerException;

    SingerDTO findById(Long id) throws CustomerException;

    List<Singer> getAll();

    String changStatus(Long id) throws  CustomerException;

}
