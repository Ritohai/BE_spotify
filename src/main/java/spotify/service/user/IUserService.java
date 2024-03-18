package spotify.service.user;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import spotify.exception.customer.CustomerException;
import spotify.model.dto.request.UserRequestLogin;
import spotify.model.dto.request.UserRequestRegister;
import spotify.model.dto.response.JwtResponse;
import spotify.model.dto.response.UserResponse;
import spotify.model.entity.Users;

import java.io.IOException;
import java.util.List;

public interface IUserService {
    JwtResponse handleLogin(UserRequestLogin userRequestLogin) throws CustomerException;
    String hanldeRegister(UserRequestRegister userRequestRegister) throws CustomerException;

    List<Users> getAll();
//    String changeStatus(Authentication authentication, Long id) throws CustomerException;
//    UserResponse findById(Long id);

//    Tải CSV
    void exportCSV(HttpServletResponse response, List<Users> list) throws IOException;
}
