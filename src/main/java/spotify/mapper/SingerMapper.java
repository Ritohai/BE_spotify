package spotify.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import spotify.model.dto.SingerDTO;
import spotify.model.entity.Singer;

@Mapper(componentModel = "spring")
public interface SingerMapper {
    SingerMapper INSTANCE = Mappers.getMapper(SingerMapper.class);

    Singer singerDTOToSinger(SingerDTO singerDTO);

    SingerDTO singerToSingerDTO(Singer singer);

}
