package spotify.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Song {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "song_id")
        private Long songId;

        @Column(name = "image")
        private String image;

        @Column(name = "music")
        private String music;

        @JoinColumn(name = "singer_id", nullable = false)
        @ManyToOne(fetch = FetchType.EAGER)
        private Singer singer;

        @JoinColumn(name = "category_id", nullable = false)
        @ManyToOne(fetch = FetchType.EAGER)
        private Category category;

        @Column(name = "lyrics")
        private String lyrics;

        @Column(name = "status")
        private Boolean songStatus;

}
