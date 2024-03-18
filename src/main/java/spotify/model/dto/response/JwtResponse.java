package spotify.model.dto.response;

import lombok.*;
import spotify.model.entity.Users;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class JwtResponse {
    private Users users;
    private String token;
    private final String type = "Bearer";
    private Set<String> roles;

}
