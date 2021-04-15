package com.win.itemsharingplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.win.itemsharingplatform.model.UsersGroup;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsersGroupRepository extends JpaRepository<UsersGroup, Long> {
    void deleteUsersGroupById(Long id);

    Optional<UsersGroup> findUsersGroupById(Long id);


//    @Query(value = "SELECT * " +
//            "FROM users_group ug , user_has_groups uhg " +
//            "WHERE uhg.user_id = :userId AND ug.id = uhg.group_id", nativeQuery = true)
    List<UsersGroup> findUsersGroupsByAdminId(Long admin_id);



}
