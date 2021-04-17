package com.win.itemsharingplatform.model;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Getter
public class UserHasGroups implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private UsersGroup group;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;


    public UserHasGroups(User user, UsersGroup group) {
        this.user = user;
        this.group = group;
    }

    public UserHasGroups() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
