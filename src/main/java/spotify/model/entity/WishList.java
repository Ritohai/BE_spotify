package spotify.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wish_list_id")
    private Long wishListId;

    @ManyToOne(fetch = FetchType.EAGER)
    private ListMusic listMusicId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false,referencedColumnName = "userId")
    private Users usersId;

    @Column(name = "status_wish")
    private Boolean statusWish;

}
