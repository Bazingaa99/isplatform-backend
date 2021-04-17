package com.win.itemsharingplatform.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Data
public class Request implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "requester_id", referencedColumnName = "id", nullable = false)
    private User requester;

    @Column(name = "accepted")
    private boolean accepted = false;

    @Column(name = "returned")
    private boolean returned = false;

    @Column(name = "share_date")
    private LocalDateTime shareDate = LocalDateTime.now();

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    //public Request(Long id) {this.item = new Item(id);}
}
