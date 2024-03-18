package spotify.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import spotify.model.dto.ListMusicDTO;
import spotify.model.entity.AddListMusic;
import spotify.model.entity.ListMusic;
import spotify.model.entity.Song;

@Mapper(componentModel = "spring")
public interface ListMusicMapper {

    ListMusicMapper INSTANCE = Mappers.getMapper(ListMusicMapper.class);

    @Mapping(source = "addListMusicId", target = "addListMusicId", qualifiedByName = "mapToAddListMusic")
    @Mapping(source = "songId", target = "songId", qualifiedByName = "mapToSong")
    ListMusic listMusicDTOToListMusic(ListMusicDTO listMusicDTO);

    @Mapping(source = "addListMusicId", target = "addListMusicId", qualifiedByName = "mapToAddListMusicId")
    @Mapping(source = "songId", target = "songId", qualifiedByName = "mapToSongId")
    ListMusicDTO listMusicToListMusicDTO(ListMusic listMusic);

    @Named("mapToSong")
    default Song mapToSong(Long songId) {
        return null; // Thay bằng mã thực tế của bạn
    }

    @Named("mapToSongId")
    default Long mapToSongId(Song song) {
        return null; // Thay bằng mã thực tế của bạn
    }

    @Named("mapToAddListMusic")
    default AddListMusic mapToAddListMusic(Long addListMusicId) {
        return null;
    }

    // Phương thức ánh xạ từ Long sang AddListMusic
    @Named("mapToAddListMusicId")
    default Long mapToAddListMusicId(AddListMusic addListMusicId) {
        return null;
    }
}
