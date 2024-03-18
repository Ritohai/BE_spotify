package spotify.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spotify.model.entity.Users;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findUsersByUserName(String userName);
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);

//    @Query("select u from Users u where ((:user is not null) and (lower(u.userName) like %:user%) or ((:user is null) and (lower(u.userName) like ''))) and ((:user is not null) and (lower(u.email) like %:email%) or ((:email is null) and (lower(u.email) like ''))) and u.status = true ")
//    Page findByUserNameOrEmail(@Param("userName") String user,
//                               @Param("email") String email, Pageable pageable);
}
