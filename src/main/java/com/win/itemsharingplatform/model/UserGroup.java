package com.win.itemsharingplatform.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class UserGroup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;

    public UserGroup(Long id){
        this.id = id;
    }

    public UserGroup(Long id, String name){
        this.id = id;
        this.setName(name);
    }

    public UserGroup() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
