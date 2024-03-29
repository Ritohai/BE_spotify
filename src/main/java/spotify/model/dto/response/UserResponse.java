package spotify.model.dto.response;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String userName;
    private String passWord;
    private Boolean status;
    private String roles;
}
