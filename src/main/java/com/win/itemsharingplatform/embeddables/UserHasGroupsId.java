package com.win.itemsharingplatform.embeddables;

import com.win.itemsharingplatform.model.User;
import com.win.itemsharingplatform.model.UsersGroup;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class UserHasGroupsId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "social_network_id")
    private UsersGroup group;

    public UserHasGroupsId(User user, UsersGroup group) {
        this.user = user;
        this.group = group;
    }

    public UserHasGroupsId() {}
}
