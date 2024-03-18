package spotify.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import spotify.exception.customer.CustomerException;
import spotify.model.dto.response.UserResponse;
import spotify.model.entity.Users;
import spotify.service.user.IUserService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping
    public ResponseEntity<List<Users>> getAllUser() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }
//    @PatchMapping("/status/{id}")
//    public ResponseEntity<String> changeStatus(@PathVariable Long id, Authentication authentication) throws CustomerException {
//        return new ResponseEntity<>(userService.changeStatus(authentication, id), HttpStatus.OK);
//    }
//    @GetMapping("/{id}")
//    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
//        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
//    }


    @GetMapping("/csv")
    public ResponseEntity<Void> exportCSV(HttpServletResponse response) throws IOException {
        List<Users> list = userService.getAll();
        userService.exportCSV(response, list);
        return ResponseEntity.ok().build();
    }
}
