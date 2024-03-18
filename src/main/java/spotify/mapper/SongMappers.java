package spotify.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import spotify.model.dto.SongDTO;
import spotify.model.entity.Song;

@Mapper(componentModel = "spring")
public interface SongMappers {
    SongMappers INSTANCE = Mappers.getMapper(SongMappers.class);

    @Mappings({
            @Mapping(target = "image", ignore = true),
            @Mapping(target = "music", ignore = true),
    })
    Song songDTOToSong(SongDTO songDTO);

    @Mappings({
            @Mapping(target = "image", ignore = true),
            @Mapping(target = "music", ignore = true),
    })
    SongDTO songToSongDTO(Song song);

}

