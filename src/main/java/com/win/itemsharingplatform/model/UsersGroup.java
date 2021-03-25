package com.win.itemsharingplatform.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
public class UsersGroup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, name = "admin_id")
    @NotBlank(message = "Please provide a Group Name.")
    @Size(max = 30, message = "Please provide a shorter group name.")
    private Long adminId;
    private String name;
    @Size(max = 100, message = "Please provide a shorter description.")
    private String description;
    @Column(nullable = false, updatable = false)
    private String groupCode;

    public UsersGroup() {}

    public UsersGroup(Long id) {
        this.id = id;
    }

    public UsersGroup(Long admin_id, String name, String description, String GroupCode) {
        this.adminId = admin_id;
        this.name = name;
        this.description = description;
        this.groupCode = GroupCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdmin_id() {
        return adminId;
    }

    public void setAdmin_id(Long admin_id) {
        this.adminId = admin_id;
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

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
}
