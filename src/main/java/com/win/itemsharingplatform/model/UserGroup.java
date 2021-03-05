package com.win.itemsharingplatform.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class UserGroup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;
}
