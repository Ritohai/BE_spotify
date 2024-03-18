package spotify.service.listMusic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import spotify.exception.customer.CustomerException;
import spotify.mapper.ListMusicMapper;
import spotify.model.dto.ListMusicDTO;
import spotify.model.dto.SongDTO;
import spotify.model.entity.AddListMusic;
import spotify.model.entity.ListMusic;
import spotify.model.entity.Song;
import spotify.model.entity.Users;
import spotify.repository.AddListMusicRepository;
import spotify.repository.ListMusicRepository;
import spotify.repository.SongRepository;
import spotify.security.userPrincipal.UserPrincipal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ListMusicImpl implements IListMusicService {
    @Autowired
    private ListMusicRepository listMusicRepository;
    @Value("${downloads-music}")
    private String downloadsMusic;

    @Autowired
    private AddListMusicRepository addListMusicRepository;

    @Autowired
    private SongRepository songRepository;

    @Override
    public String createSong(ListMusicDTO listMusicDTO, Authentication authentication) throws CustomerException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (userPrincipal == null) {
            throw new CustomerException("Yêu cầu đăng nhập.");
        }
        Users users = userPrincipal.getUsers();
        AddListMusic addListMusic = addListMusicRepository.findById(listMusicDTO.getAddListMusicId()).orElseThrow(() -> new CustomerException("Id không tồn tại."));
        Song song = songRepository.findById(listMusicDTO.getSongId()).orElseThrow(() -> new CustomerException("Không tìm thấy id."));
        if (listMusicRepository.existsByAddListMusicIdAndSongIdAndAddListMusicId_UserId(addListMusic, song, users)) {
            throw new CustomerException("Đã tồn tại.");
        }
        ListMusic listMusic = ListMusicMapper.INSTANCE.listMusicDTOToListMusic(listMusicDTO);
        listMusic.setAddListMusicId(addListMusic);
        listMusic.setSongId(song);
        listMusicRepository.save(listMusic);
        return "Thêm bài hát thành công.";
    }

    @Override
    public List<ListMusic> getAll(Authentication authentication) throws CustomerException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (userPrincipal == null) {
            throw new CustomerException("Yêu cầu đăng nhập.");
        }
        Users users = userPrincipal.getUsers();
        return listMusicRepository.getAllByAddListMusicId_UserId(users);
    }

    @Override
    public String updateListSong(Authentication authentication, ListMusicDTO listMusicDTO, Long id) throws CustomerException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (userPrincipal == null) {
            throw new CustomerException("Yêu cầu đăng nhập.");
        }
        Users users = userPrincipal.getUsers();
        AddListMusic addListMusic = addListMusicRepository.findById(listMusicDTO.getAddListMusicId()).orElseThrow(() -> new CustomerException("Id không tồn tại."));
        Song song = songRepository.findById(listMusicDTO.getSongId()).orElseThrow(() -> new CustomerException("Không tìm thấy id."));
        Optional<ListMusic> optionalListMusic = listMusicRepository.findById(id);
        if (listMusicRepository.existsByAddListMusicIdAndSongIdAndAddListMusicId_UserId(addListMusic,song, users)) {
            ListMusic listMusic = optionalListMusic.get();
            listMusic.setListMusicId(id);
            if (listMusicDTO.getAddListMusicId() == null) {
                listMusic.setAddListMusicId(listMusic.getAddListMusicId());
            } else {
                listMusic.setAddListMusicId(addListMusic);
            }
            if (listMusic.getSongId() == null) {
                listMusic.setSongId(listMusic.getSongId());
            } else {
                listMusic.setSongId(song);
            }
            listMusicRepository.save(listMusic);
            return "Sửa thành công.";
        }
        throw new CustomerException("không tìm thấy id.");
    }

    @Override
    public ListMusic findByIdList(Authentication authentication, Long id) throws CustomerException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (userPrincipal == null) {
            throw new CustomerException("Yêu cầu đăng nhập.");
        }
        Users users = userPrincipal.getUsers();
        Optional<ListMusic> optional = listMusicRepository.findById(id);
        if (listMusicRepository.existsByListMusicIdAndAddListMusicId_UserId(id, users)) {
            ListMusic listMusic = new ListMusic();
            listMusic.setListMusicId(id);
            listMusic.setAddListMusicId(optional.get().getAddListMusicId());
            listMusic.setSongId(optional.get().getSongId());
            return listMusic;
        }
        throw new CustomerException("Không tìm thấy id.");
    }

    @Override
    public String deleteListSong(Authentication authentication, Long id) throws CustomerException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (userPrincipal == null) {
            throw new CustomerException("Yêu cầu đăng nhập.");
        }
        Users users = userPrincipal.getUsers();
        if (listMusicRepository.existsByListMusicIdAndAddListMusicId_UserId(id, users)) {
            listMusicRepository.deleteById(id);
            return "Xóa thành công.";
        }
        throw new CustomerException("Không tìm thấy id.");
    }

//    Download music


    @Override
    public String downloadsMusic(Authentication authentication, Long id) throws CustomerException, IOException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (userPrincipal == null) {
            throw new CustomerException("Yêu cầu đăng nhập.");
        }
        Optional<ListMusic> optional = listMusicRepository.findById(id);

        if (optional.isPresent()) {
            String fileUrl = optional.get().getSongId().getMusic();
            if (fileUrl != null) {
                // Kiểm tra và tạo thư mục Downloads nếu chưa tồn tại
                File downloadsDir = new File("C:\\Users\\ADMIN\\Desktop\\Spotify\\music\\");
                if (!downloadsDir.exists()) {
                    downloadsDir.mkdirs();
                }

                // Xây dựng URL và mở kết nối
                String encodedHost = URLEncoder.encode("music.com", StandardCharsets.UTF_8);
                String encodedUrl = "http://" + encodedHost + "/" + fileUrl;
                UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(encodedUrl);
                URL url = builder.build().toUri().toURL();
                URLConnection connection = url.openConnection();

                // Ghi dữ liệu vào thư mục Downloads
                try (InputStream in = connection.getInputStream();
                     FileOutputStream out = new FileOutputStream(downloadsDir.getAbsolutePath() + File.separator + Paths.get(url.getPath()).getFileName())) {
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                }
                return "Tải thành công.";
            }

        }

        throw new CustomerException("Tải không thành công.");
    }
}
