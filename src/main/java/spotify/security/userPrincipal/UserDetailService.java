package spotify.security.userPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spotify.model.entity.Users;
import spotify.repository.UserRepository;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> optionalUsers = userRepository.findUsersByUserName(username);
        if (optionalUsers.isPresent()) {
            Users users = optionalUsers.get();
            UserPrincipal userPrincipal = UserPrincipal.builder()
                    .users(users)
                    .authorities(users.getRoles().stream().map(item -> new SimpleGrantedAuthority(item.getRoleName())).toList())
                    .build();
            return userPrincipal;
        } else {
            throw new UsernameNotFoundException(username + " not found.");

        }
    }
}
