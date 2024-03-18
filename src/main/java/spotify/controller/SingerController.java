package spotify.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spotify.exception.customer.CustomerException;
import spotify.model.dto.SingerDTO;
import spotify.model.entity.Singer;
import spotify.service.singer.ISingerService;

import java.util.List;

@RestController
@RequestMapping("/admin/singer")
public class SingerController {
    @Autowired
    private ISingerService singerService;

//    TODO: Thêm ca sĩ
    @PostMapping
    public ResponseEntity<String> createSinger(@Valid @RequestBody SingerDTO singerDTO) throws CustomerException {
        return new ResponseEntity<>(singerService.createSinger(singerDTO), HttpStatus.CREATED);
    }

//    TODO: Sửa tên ca sĩ
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSinger(@RequestBody SingerDTO singerDTO, @PathVariable Long id) throws CustomerException {
        return new ResponseEntity<>(singerService.updateSinger(id, singerDTO), HttpStatus.OK);
    }

//    TODO: Tìm tên ca sĩ theo id
    @GetMapping("/{id}")
    public ResponseEntity<SingerDTO> findById(@PathVariable Long id) throws CustomerException {
        return new ResponseEntity<>(singerService.findById(id), HttpStatus.OK);
    }

//    TODO: Hiển thị tất cả các ca sĩ
    @GetMapping("/all")
    public ResponseEntity<List<Singer>> getAll() {
        return new ResponseEntity<>(singerService.getAll(), HttpStatus.OK);
    }

//    TODO: Thay đổi trạng thái
    @PatchMapping("/status/{id}")
    public ResponseEntity<String> changStatus(@PathVariable Long id) throws CustomerException {
        return new ResponseEntity<>(singerService.changStatus(id), HttpStatus.OK);
    }
}
