package spotify.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import spotify.model.dto.WishListDTO;
import spotify.model.entity.WishList;

@Mapper(componentModel = "spring")
public interface WishListMapper {

    WishListMapper INSTANCE = Mappers.getMapper(WishListMapper.class);

    WishList wishListDTOToWishList(WishListDTO wishListDTO);

    WishListDTO wishListToWishListSTO(WishList wishList);
}
