package spotify.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SingerDTO {
    @NotEmpty(message = "Không để trống.")
    private String singerName;
}
