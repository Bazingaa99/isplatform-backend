package com.win.itemsharingplatform.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<Item> items;
}
