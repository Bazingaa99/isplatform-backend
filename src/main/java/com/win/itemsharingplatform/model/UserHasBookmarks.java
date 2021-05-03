package com.win.itemsharingplatform.model;

import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class UserHasBookmarks {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @NotNull
    private Item item;

    public UserHasBookmarks(User user, Item item) {
        this.user = user;
        this.item = item;
    }

    public UserHasBookmarks() {

    }
}
