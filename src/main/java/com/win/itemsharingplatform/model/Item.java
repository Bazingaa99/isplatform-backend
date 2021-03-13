package com.win.itemsharingplatform.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name="category_id", nullable = false)
    private Category category;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    private int duration;

    @Column(name = "picture_id")
    private String pictureId;

    @Column(name = "date_created")
    @CreationTimestamp
    private Date dateCreated;

    @Column(name = "view_count")
    private int viewCount;

    public Item() {}

    public Item(Long id) {
        this.id = id;
    }

    public Item(long ownerId, long groupId, Long category, String name, String description, int duration, String pictureId, Date dateCreated, int viewCount){
//        this.ownerId = ownerId;
//        this.groupId = groupId;
//        this.categoryId = category;
        this.name = name;
        this.description = description;
        this.duration = duration;
//        this.pictureId = pictureId;
//        this.dateCreated = dateCreated;
//        this.viewCount = viewCount;
    }

    public Item(String name, String description, int duration){
        this.name = name;
        this.description = description;
        this.duration = duration;
    }

    public Long getId(){
        return id;
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

    @Override
    public String toString() {
        return name + " | " + description;
    }
}
