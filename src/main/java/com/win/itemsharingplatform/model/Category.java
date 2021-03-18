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

    @OneToMany(targetEntity =Item.class, mappedBy = "category", fetch = FetchType.LAZY)
    private List<Item> items;

    public Category(Long id){
        this.id = id;
    }

    public Category(Long id, String categoryName){
        this.id = id;
        this.setCategoryName(categoryName);
    }

    public Category() {}

    public Long getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
