package com.win.itemsharingplatform.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    //private Long ownerId;
    //private Long groupId;
    //private Long categoryId;
    private String name;
    private String description;
    private int duration;
    //private String pictureId;
    //private Date dateCreated;
    //private int viewCount;

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

//    public Long getOwnerId() {
//        return ownerId;
//    }
//
//    public void setOwnerId(Long ownerId) {
//        this.ownerId = ownerId;
//    }
//
//    public long getGroupId() {
//        return groupId;
//    }
//
//    public void setGroupId(Long groupId) {
//        this.groupId = groupId;
//    }
//
//    public Long getCategory() {
//        return categoryId;
//    }
//
//    public void setCategory(Long categoryId) {
//        this.categoryId = categoryId;
//    }

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

//    public String getPictureId() {
//        return pictureId;
//    }
//
//    public void setPictureId(String pictureId) {
//        this.pictureId = pictureId;
//    }
//
//    public Date getDateCreated() {
//        return dateCreated;
//    }
//
//    public void setDateCreated(Date dateCreated) {
//        this.dateCreated = dateCreated;
//    }
//
//    public int getViewCount() {
//        return viewCount;
//    }
//
//    public void setViewCount(int viewCount) {
//        this.viewCount = viewCount;
//    }

    @Override
    public String toString() {
        return name + " | " + description;
    }
}
