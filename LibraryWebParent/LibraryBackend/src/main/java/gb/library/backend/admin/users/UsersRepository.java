package gb.library.backend.admin.users;

import gb.library.backend.admin.utils.paging.SearchRepository;
import gb.library.common.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;


@Repository
public interface UsersRepository extends SearchRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.email LIKE %?1% OR u.firstName LIKE %?1% OR u.lastName LIKE %?1%")
    Page<User> getAllWithFilter(String keyword, Pageable pageable);

    User findByEmail(String email);

    User save(User user);

    @Modifying
    @Query("UPDATE User u SET u.enabled = ?2, u.updatedAt = ?3 WHERE u.id = ?1")
    public void updateEnabledStatus(Integer id, boolean enabled, Instant dateTime);

}
