package spotify.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongDTO {

    @JsonIgnore
    private MultipartFile image;
    @JsonIgnore
    private MultipartFile music;
    @NotNull(message = "Không để trống.")
    private Long singerId;
    @NotNull(message = "Không để trống.")
    private Long categoryId;
    @NotEmpty(message = "Không để trống.")
    private String lyrics;

}
