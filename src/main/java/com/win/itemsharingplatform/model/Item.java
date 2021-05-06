package com.win.itemsharingplatform.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Data
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name="group_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "Please select a group.")
    private UsersGroup group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name="category_id", referencedColumnName = "id",  nullable = false)
    @NotNull(message = "Please select a category.")
    private Category category;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Please provide a title.")
    @Size(max = 50, message = "Please provide a shorter title.")
    private String name;

    @Column(name = "description")
    @NotBlank(message = "Please provide a description.")
    @Size(max=50, message="Please provide a shorter description.")
    private String description;

    @Column(name = "duration", nullable = false)
    @NotNull(message = "Please provide a duration.")
    @Min(value = 1, message="You have to lend the item at least for a day.")
    @Max(value = 180, message = "You can lend the item for a maximum of 180 days.")
    private int duration;

    @Lob
    private byte[] image;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "date_created")
    @CreationTimestamp
    private Date dateCreated;

    @Column(name = "view_count")
    private int viewCount;

    @Column(name = "is_hidden")
    private boolean hidden = false;

    @Column(name = "is_available")
    private boolean available = true;

    @Column(name = "bookmark_count")
    private int bookmarkCount = 0;

    public Item() {}

    public Item(Long id) {
        this.id = id;
    }
}
