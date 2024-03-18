package spotify.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spotify.exception.customer.CustomerException;
import spotify.model.dto.request.UserRequestLogin;
import spotify.model.dto.request.UserRequestRegister;
import spotify.model.dto.response.JwtResponse;
import spotify.service.user.IUserService;

@RestController
@RequestMapping("/auth")
public class AuthenController {
    @Autowired
    private IUserService userService;
    @PostMapping("/register")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequestRegister userRequestRegister) throws CustomerException {
        return new ResponseEntity<>(userService.hanldeRegister(userRequestRegister), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody UserRequestLogin userRequestLogin) throws CustomerException{
        return new ResponseEntity<>(userService.handleLogin(userRequestLogin), HttpStatus.OK);
    }
}
