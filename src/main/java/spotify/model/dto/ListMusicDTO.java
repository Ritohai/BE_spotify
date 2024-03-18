package spotify.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListMusicDTO {
    @NotNull(message = "Không để trống.")
    private Long addListMusicId;
    @NotNull(message = "Không để trống.")
    private Long songId;

}
