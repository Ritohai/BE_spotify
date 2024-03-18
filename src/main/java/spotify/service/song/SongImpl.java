package spotify.service.song;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import spotify.exception.customer.CustomerException;
import spotify.mapper.SongMappers;
import spotify.model.dto.SongDTO;
import spotify.model.entity.Category;
import spotify.model.entity.Singer;
import spotify.model.entity.Song;
import spotify.repository.CategoryRepository;
import spotify.repository.SingerRepository;
import spotify.repository.SongRepository;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SongImpl implements ISongService {
    @Value("${path-upload}")
    private String pathUpload;
    @Value("${music}")
    private String music;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private SingerRepository singerRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SongMappers songMappers;


    @Override
    public String   createSong(SongDTO songDTO) throws CustomerException, IOException {
        MultipartFile multipartFile = songDTO.getImage();
        MultipartFile multipartMusic = songDTO.getMusic();
        Category category = categoryRepository.findById(songDTO.getCategoryId()).orElseThrow(() -> new CustomerException("Không tìm thấy id danh mục."));
        Singer singer = singerRepository.findById(songDTO.getSingerId()).orElseThrow(() -> new CustomerException("Không tìm thấy id ca sĩ."));

        if (multipartFile != null && !multipartFile.isEmpty() && !multipartMusic.isEmpty()) {
//            File nhạc
            String fileMusic = songDTO.getMusic().getOriginalFilename();
            FileCopyUtils.copy(songDTO.getMusic().getBytes(), new File(music + fileMusic));

//            File ảnh
            String fileImage = songDTO.getImage().getOriginalFilename();
            FileCopyUtils.copy(songDTO.getImage().getBytes(), new File(pathUpload + fileImage));

            Song song = SongMappers.INSTANCE.songDTOToSong(songDTO);
            song.setCategory(category);
            song.setSinger(singer);
            song.setMusic(fileMusic);
            song.setImage(fileImage);
            song.setSongStatus(true);
            songRepository.save(song);
            return "Thêm bài hát thành công.";
        }
        throw new CustomerException("");
    }

    @Override
    public String updateSong(SongDTO songDTO, Long id) throws CustomerException {
        Optional<Song> optional = songRepository.findById(id);
        if (optional.isPresent()) {
            Song song = SongMappers.INSTANCE.songDTOToSong(songDTO);
            song.setSongId(id);
//            Image
            if (songDTO.getImage() == null) {
                song.setImage(optional.get().getImage());
            } else {
                song.setImage(songDTO.getImage().getOriginalFilename());
            }
//              Music
            if (songDTO.getMusic() == null) {
                song.setMusic(optional.get().getMusic());
            } else {
                song.setMusic(songDTO.getMusic().getOriginalFilename());
            }
//              Category
            if (songDTO.getCategoryId() == null) {
                song.setCategory(optional.get().getCategory());
            } else {
                Category category = categoryRepository.findById(songDTO.getCategoryId()).orElseThrow(() -> new CustomerException("Không tìm thấy id danh mục."));
                song.setCategory(category);
            }
//            Singer
            if (songDTO.getSingerId() == null) {
                song.setSinger(optional.get().getSinger());
            } else {
                Singer singer = singerRepository.findById(songDTO.getSingerId()).orElseThrow(() -> new CustomerException("Không tìm thấy id ca sĩ."));
                song.setSinger(singer);
            }
            song.setSongStatus(optional.get().getSongStatus());
            songRepository.save(song);
            return "Sửa thành công.";
        }
        throw new CustomerException("Không tìm thấy id.");
    }

    @Override
    public Song findById(Long id) throws CustomerException {
        Optional<Song> optional = songRepository.findById(id);
        if (optional.isPresent()) {
            Song song = new Song();
            song.setSongId(id);
            song.setImage(optional.get().getImage());
            song.setMusic(optional.get().getMusic());
            song.setCategory(optional.get().getCategory());
            song.setSinger(optional.get().getSinger());
            song.setLyrics(optional.get().getLyrics());
            song.setSongStatus(optional.get().getSongStatus());
            return song;
        }
        throw new CustomerException("Không thấy id.");
    }

    @Override
    public List<Song> getAll() {
        List<Song> list = new ArrayList<>();
        for (Song s : songRepository.findAll()) {
            if (s.getSongStatus()) {
                list.add(s);
            }
        }
        return list;
    }

    @Override
    public String changStatus(Long id) throws CustomerException {
        Optional<Song> optional = songRepository.findById(id);
        if (optional.isPresent()) {
            Song song = optional.get();
            song.setSongStatus(!optional.get().getSongStatus());
            songRepository.save(song);
            return "Thay đổi trạng thái thành công.";
        }
        throw new CustomerException("Không tim thấy id.");
    }


//    Tải CSV

    @Override
    public void exportCSV(HttpServletResponse response,List<Song> list) throws IOException {
        response.setContentType("song/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=songcsv.csv");
        try (PrintWriter writer=response.getWriter();
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("id","image","music", "singer_name", "category_name", "lyrics", "status"))) {
            for (Song song: list) {
                csvPrinter.printRecord(song.getSongId(), song.getImage(), song.getMusic(), song.getSinger().getSingerName(), song.getCategory().getCategoryName(), song.getLyrics(), song.getSongStatus());
            }
        }
    }
}
