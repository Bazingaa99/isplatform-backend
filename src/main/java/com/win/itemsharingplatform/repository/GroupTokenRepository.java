package com.win.itemsharingplatform.repository;

import com.win.itemsharingplatform.model.GroupToken;
import com.win.itemsharingplatform.model.UsersGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface GroupTokenRepository extends JpaRepository<GroupToken, Long> {

    Optional<GroupToken> findByToken(String token);



}
