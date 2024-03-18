package spotify.service.user;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import spotify.exception.customer.CustomerException;
import spotify.model.dto.request.UserRequestLogin;
import spotify.model.dto.request.UserRequestRegister;
import spotify.model.dto.response.JwtResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import spotify.model.dto.response.UserResponse;
import spotify.model.entity.Role;
import spotify.model.entity.Users;
import spotify.repository.RoleRepository;
import spotify.repository.UserRepository;
import spotify.security.jwt.JwtProvider;
import spotify.security.userPrincipal.UserPrincipal;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

//    TODO: Đăng nhập
    @Override
    public JwtResponse handleLogin(UserRequestLogin userRequestLogin) throws CustomerException {
        Authentication authentication;
        try {
            authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(userRequestLogin.getUserName(), userRequestLogin.getPassWord()));
        } catch (AuthenticationException ax) {
            throw new CustomerException("Tài khoản hoặc mật khẩu sai.");
        }
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (!userPrincipal.getUsers().getStatus()) {
            throw new CustomerException("Tài khoản bị khóa.");
        }
        String token = jwtProvider.generateToken(userPrincipal);
        return JwtResponse.builder()
                .users(userPrincipal.getUsers())
                .token(token)
                .roles(userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .build();
    }

//    TODO: Đăng kí
    @Override
    public String hanldeRegister(UserRequestRegister userRequestRegister) throws CustomerException {
        if (userRepository.existsByUserName(userRequestRegister.getUserName())) {
            throw new CustomerException("Tài khoản đã tồn tại.");
        }
        if (userRepository.existsByEmail(userRequestRegister.getEmail())) {
            throw new CustomerException("Email đã tồn tại.");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByRoleName("ROLE_USER").get());
        Users users = Users.builder()
                .email(userRequestRegister.getEmail())
                .userName(userRequestRegister.getUserName())
                .passWord(passwordEncoder.encode(userRequestRegister.getPassWord()))
                .roles(roles)
                .status(true)
                .build();
        userRepository.save(users);
        return "Đăng kí thành công.";
    }


    //   TODO: Tạo TK admin mặc định
    @PostConstruct
    public void init() {
        try {
            // Tạo tài khoản admin nếu chưa tồn tại

            if (!userRepository.existsByUserName("admin123") && !userRepository.existsByEmail("admin123@gmaill.com")) {
                Set<Role> adminRoles = new HashSet<>();

                Role role1 = new Role(1L, "ROLE_ADMIN");
                Role role2 = new Role(2L, "ROLE_USER");
                roleRepository.save(role1);
                roleRepository.save(role2);
                adminRoles.add(roleRepository.findByRoleName("ROLE_ADMIN").get());
                if (userRepository.existsByUserName("admin123") && userRepository.existsByEmail("admin123@gmail.com")) {
                    throw new CustomerException("Đã tồn tại.");
                } else {
                    Users adminUser = Users.builder()
                            .email("admin123@gmail.com")
                            .userName("admin123")
                            .passWord(passwordEncoder.encode("admin123"))
                            .status(true)
                            .roles(adminRoles)
                            .build();
                    userRepository.save(adminUser);
                }
            } else {
                throw new CustomerException("Đã tồn tại.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//  TODO: Thay đổi trạng thái người dùng
//    @Override
//    public String changeStatus(Authentication authentication, Long id) throws CustomerException {
//        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
//        Users users = userPrincipal.getUsers();
//        if (users.getRoles().stream().anyMatch(role -> role.getRoleId() == 2)) {
//            Optional<Users> optionalUser = userRepository.findById(id);
//            if (optionalUser.isPresent() && id != 1) {
//                Users user = optionalUser.get();
//                user.setStatus(!user.getStatus());
//                user = userRepository.save(user);
//                if (user.getStatus()) {
//                    return "Đã mở khóa tài khoản!";
//                }else {
//                    return "Đã khóa tài khoản!";
//                }
//            } else {
//                throw new CustomerException("Không thấy người dùng.");
//            }
//        }else {
//
//            throw new CustomerException("Không có quyền sửa.");
//        }
//    }
////  TODO: Tìm kiếm
//    @Override
//    public UserResponse findById(Long id) {
//        Optional<Users> optionalUser = userRepository.findById(id);
//        return UserResponse.builder()
//                .id(optionalUser.get().getUserId())
//                .email(optionalUser.get().getEmail())
//                .userName(optionalUser.get().getUserName())
//                .passWord(optionalUser.get().getPassWord())
//                .status(optionalUser.get().getStatus())
//                .roles(optionalUser.get().getRoles().toString())
//                .build();
//
//    }

//    TODO : Phân trang theo tên, email


//    TODO: List người dùng
    @Override
    public List<Users> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void exportCSV(HttpServletResponse response, List<Users> list) throws IOException {
        response.setContentType("user/text");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=usercsv.csv");
        try(PrintWriter writer = response.getWriter();
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("id", "email", "user_name", "pass_word", "roles", "status"))) {
            for (Users u:list) {
                List<String> roles = u.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList());
                csvPrinter.printRecord(u.getUserId(), u.getEmail(), u.getUserName(), u.getPassWord(),String.join(",", roles), u.getStatus());
            }
        }
    }
}
