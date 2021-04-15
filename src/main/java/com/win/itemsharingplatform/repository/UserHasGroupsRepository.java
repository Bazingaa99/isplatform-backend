package com.win.itemsharingplatform.repository;

import com.win.itemsharingplatform.model.User;
import com.win.itemsharingplatform.model.UserHasGroups;
import com.win.itemsharingplatform.model.UsersGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserHasGroupsRepository extends JpaRepository<UserHasGroups, Long> {

    Optional<UserHasGroups> findByGroupIdAndUserId(Long usersGroupId,Long id);
}
