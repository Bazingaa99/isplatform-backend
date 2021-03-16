package com.win.itemsharingplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.win.itemsharingplatform.model.UsersGroup;

import java.util.Optional;

public interface UsersGroupRepository extends JpaRepository<UsersGroup, Long> {
    void deleteUsersGroupById(Long id);

    Optional<UsersGroup> findUsersGroupById(Long id);
}
