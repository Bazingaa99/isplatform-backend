package com.win.itemsharingplatform.repository;

import com.win.itemsharingplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;
@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);


    @Query("SELECT isEnabled from User where email=?1")
    boolean isUserEnabled (String email);

    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.isEnabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);

    @Query("SELECT u from UserHasGroups u where u.user.id=?2 and u.group.id=?1")
    Optional<User> findIfUserExistsByIdAndUserId(Long groupId, Long userId);
}
