package com.win.itemsharingplatform.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    @NotNull(message = "Provide a request id.")
    private Request request;

    @OneToMany(
            cascade = CascadeType.REMOVE,
            mappedBy = "chat"
    )
    private List<Message> messages;

    public Chat(Long id){
        this.id = id;
    }

    public Chat(){}
}
