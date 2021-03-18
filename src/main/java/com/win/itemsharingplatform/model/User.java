package com.win.itemsharingplatform.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public User() {}

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
