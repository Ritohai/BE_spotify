package spotify.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import spotify.exception.customer.CustomerException;
import spotify.model.dto.AddListMusicDTO;
import spotify.model.entity.AddListMusic;
import spotify.service.AddListMusic.IAddListMusicService;

import java.util.List;

@RestController
@RequestMapping("/user/addList")
public class AddListMusicController {
    @Autowired
    private IAddListMusicService addListMusicService;


    @PostMapping
    public ResponseEntity<String> createAddListMusic(@Valid @RequestBody AddListMusicDTO addListMusicDTO, Authentication authentication) throws CustomerException {
        return new ResponseEntity<>(addListMusicService.createListMusic(authentication,addListMusicDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AddListMusic>> getAll(Authentication authentication) throws CustomerException {
        return new ResponseEntity<>(addListMusicService.getAllList(authentication), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(Authentication authentication,@RequestBody AddListMusicDTO addListMusicDTO,@PathVariable Long id) throws CustomerException {
        return new ResponseEntity<>(addListMusicService.updateListMusic(authentication, addListMusicDTO, id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddListMusic> findById(Authentication authentication, @PathVariable Long id) throws CustomerException {
        return new ResponseEntity<>(addListMusicService.findByIdMusic(authentication,id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(Authentication authentication, @PathVariable Long id) throws CustomerException {
        return new ResponseEntity<>(addListMusicService.delete(authentication, id), HttpStatus.OK);
    }

}
