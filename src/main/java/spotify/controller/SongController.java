package spotify.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spotify.exception.customer.CustomerException;
import spotify.model.dto.SongDTO;
import spotify.model.entity.Song;
import spotify.service.song.ISongService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/song")
public class SongController {
    @Autowired
    private ISongService songService;

    @PostMapping
    public ResponseEntity<String> createSong(@Valid @ModelAttribute SongDTO songDTO) throws CustomerException, IOException {
        return new ResponseEntity<>(songService.createSong(songDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSong(@ModelAttribute SongDTO songDTO, @PathVariable Long id) throws CustomerException {
        return new ResponseEntity<>(songService.updateSong(songDTO,id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> findById(@PathVariable Long id) throws CustomerException {
        return new ResponseEntity<>(songService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Song>> getAll() {
        return new ResponseEntity<>(songService.getAll(), HttpStatus.OK);
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<String> changStatus(@PathVariable Long id) throws CustomerException {
        return new ResponseEntity<>(songService.changStatus(id), HttpStatus.OK);
    }

//    Tải CSV
    @GetMapping("/csv")
    public ResponseEntity<Void> exportCSV(HttpServletResponse response) throws IOException {
        List<Song> data = songService.getAll();
        songService.exportCSV(response, data);
        return ResponseEntity.ok().build();
    }
}
