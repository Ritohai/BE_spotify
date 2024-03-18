package spotify.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import spotify.exception.customer.CustomerException;
import spotify.model.dto.ListMusicDTO;
import spotify.model.entity.ListMusic;
import spotify.service.listMusic.IListMusicService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user/listMusic")
public class ListMusicController {
    @Autowired
    private IListMusicService listMusicService;

    @PostMapping
    public ResponseEntity<String> createSong(@Valid @RequestBody ListMusicDTO listMusicDTO, Authentication authentication) throws CustomerException {
        return new ResponseEntity<>(listMusicService.createSong(listMusicDTO, authentication), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ListMusic>> getAll(Authentication authentication) throws CustomerException {
        return new ResponseEntity<>(listMusicService.getAll(authentication), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListMusic> findByIdListMusic(@PathVariable Long id, Authentication authentication) throws CustomerException {
        return new ResponseEntity<>(listMusicService.findByIdList(authentication, id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateListMusic(@PathVariable Long id, Authentication authentication, @RequestBody ListMusicDTO listMusicDTO) throws CustomerException {
        return new ResponseEntity<>(listMusicService.updateListSong(authentication, listMusicDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteListMusic(Authentication authentication, @PathVariable Long id) throws CustomerException {
        return new ResponseEntity<>(listMusicService.deleteListSong(authentication,id), HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<String> download(Authentication authentication, @PathVariable Long id) throws CustomerException, IOException {
        try {
            String message = listMusicService.downloadsMusic(authentication, id);
            return ResponseEntity.ok(message);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi tải xuống tệp.");
        }
    }

}
