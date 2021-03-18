package com.win.itemsharingplatform.model;

import com.win.itemsharingplatform.embeddables.UserHasGroupsId;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class UserHasGroups implements Serializable {

    @EmbeddedId
    private UserHasGroupsId id;
}
