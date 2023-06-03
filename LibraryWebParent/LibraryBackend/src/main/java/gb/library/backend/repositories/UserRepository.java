package gb.library.backend.repositories;

import gb.library.common.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(Integer id);

    User findUserByEmail(String email);

    User findUserByVerificationCode(String verificationCode);

    @Query("UPDATE User u SET u.enabled = true, u.verificationCode = null WHERE u.id = ?1")
    @Modifying
    void enable(Integer id);
}
