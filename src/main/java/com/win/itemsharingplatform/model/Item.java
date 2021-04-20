package com.win.itemsharingplatform.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
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

    @Column(name = "picture_id")
    private String pictureId;

    @Column(name = "date_created")
    @CreationTimestamp
    private Date dateCreated;

    @Column(name = "view_count")
    private int viewCount;

    @Column(name = "is_hidden")
    private boolean isHidden;

    public Item() {}

    public Item(Long id) {
        this.id = id;
    }

    public Item(User owner, UsersGroup group, Category category, String name, String description, int duration, String pictureId, Date dateCreated, int viewCount, boolean isHidden){
        this.owner = owner;
        this.group = group;
        this.category = category;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.pictureId = pictureId;
        this.dateCreated = dateCreated;
        this.viewCount = viewCount;
        this.isHidden = isHidden;
    }

//    public Item(String name, String description, int duration, String pictureId, int viewCount){
//        this.name = name;
//        this.description = description;
//        this.duration = duration;
//        this.viewCount = viewCount;
//    }

    public Long getId(){
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public UsersGroup getGroup() {
        return group;
    }

    public void setGroup(UsersGroup group) {
        this.group = group;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
    public boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    @Override
    public String toString() {
        return name + " | " + description;
    }
}
