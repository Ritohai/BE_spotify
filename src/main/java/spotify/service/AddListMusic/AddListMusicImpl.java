package spotify.service.AddListMusic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import spotify.exception.customer.CustomerException;
import spotify.mapper.AddListMusicMapper;
import spotify.model.dto.AddListMusicDTO;
import spotify.model.entity.AddListMusic;
import spotify.model.entity.Users;
import spotify.repository.AddListMusicRepository;
import spotify.security.userPrincipal.UserPrincipal;

import java.util.List;
import java.util.Optional;

@Service
public class AddListMusicImpl implements IAddListMusicService {
    @Autowired
    private AddListMusicRepository addListMusicRepository;

    @Override
    public String createListMusic(Authentication authentication, AddListMusicDTO addListMusicDTO) throws CustomerException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (userPrincipal == null) {
            throw new CustomerException("Yêu cầu đăng nhập.");
        }
        Users users = userPrincipal.getUsers();
        if (addListMusicRepository.existsByNameListAndUserId(addListMusicDTO.getNameList(), users)) {
            throw new CustomerException("Đã tồn tại tên danh sách.");
        }
        AddListMusic addListMusic = AddListMusicMapper.INSTANCE.addListMusicDTOToAddListMusic(addListMusicDTO);
        addListMusic.setUserId(users);
        addListMusicRepository.save(addListMusic);
        return "Thêm danh sách thành công.";
    }

    @Override
    public List<AddListMusic> getAllList(Authentication authentication) throws CustomerException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (userPrincipal == null) {
            throw new CustomerException("Yêu cầu đăng nhập.");
        }
        Users users = userPrincipal.getUsers();
        return addListMusicRepository.getAllByUserId(users);
    }

    @Override
    public String updateListMusic(Authentication authentication, AddListMusicDTO addListMusicDTO, Long id) throws CustomerException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (userPrincipal == null) {
            throw new CustomerException("Yêu cầu đăng nhập.");
        }
        Users users = userPrincipal.getUsers();
        if (addListMusicRepository.existsByNameListAndUserId(addListMusicDTO.getNameList(), users)) {
            throw new CustomerException("Đã tồn tại.");
        }
        Optional<AddListMusic> optional = addListMusicRepository.findById(id);
        if (optional.isPresent()) {
            AddListMusic addListMusic = new AddListMusic();
            addListMusic.setListId(id);
            addListMusic.setNameList(addListMusicDTO.getNameList());
            addListMusic.setUserId(users);
            addListMusicRepository.save(addListMusic);
            return "Sửa thành công.";
        }
        throw new CustomerException("Không tìm thấy id.");
    }

    @Override
    public AddListMusic findByIdMusic(Authentication authentication, Long id) throws CustomerException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (userPrincipal == null) {
            throw new CustomerException("Yêu cầu đăng nhập.");
        }
        Users users = userPrincipal.getUsers();
        Optional<AddListMusic> optional = addListMusicRepository.findById(id);
        if (addListMusicRepository.existsByListIdAndUserId(id, users)) {
            AddListMusic addListMusic = new AddListMusic();
            addListMusic.setListId(id);
            addListMusic.setNameList(optional.get().getNameList());
            addListMusic.setUserId(users);
            return addListMusic;
        }
        throw new CustomerException("Không tìm thấy id.");
    }

    @Override
    public String delete(Authentication authentication, Long id) throws CustomerException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (userPrincipal == null) {
            throw new CustomerException("Yêu cầu đăng nhập.");
        }
        Users users = userPrincipal.getUsers();
        if (addListMusicRepository.existsByListIdAndUserId(id, users)) {
            addListMusicRepository.deleteById(id);
            return "Xóa thành công.";
        }
        throw new CustomerException("Không tìm thấy id.");
    }
}
