package com.win.itemsharingplatform.model;

import com.win.itemsharingplatform.repository.GroupTokenRepository;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Entity
public class GroupToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime expiresAt;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name="group_id"
    )
    private UsersGroup usersGroup;

    public GroupToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt,UsersGroup usersGroup) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.usersGroup=usersGroup;
    }

    public GroupToken() {

    }
}
