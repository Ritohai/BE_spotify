package spotify.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import spotify.model.dto.AddListMusicDTO;
import spotify.model.entity.AddListMusic;
import spotify.model.entity.Users;

@Mapper(componentModel = "spring")
public interface AddListMusicMapper {
    AddListMusicMapper INSTANCE = Mappers.getMapper(AddListMusicMapper.class);

    @Mapping(source = "userId", target = "userId")
    AddListMusic addListMusicDTOToAddListMusic(AddListMusicDTO addListMusicDTO);
    @Mapping(source = "userId", target = "userId")
    AddListMusicDTO addListMusicToAddListMusicDTO(AddListMusic addListMusic);


    default Users map(Long userId) {
        if (userId == null) {
            return null;
        }
        Users user = new Users();
        user.setUserId(userId);
        return user;
    }

    default Long map(Users user) {
        if (user == null) {
            return null;
        }
        return user.getUserId();
    }
}
