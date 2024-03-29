package com.win.itemsharingplatform.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    @NotNull
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "requester_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private User requester;

    @Column(name = "accepted",columnDefinition = "boolean")
    @NotNull
    private boolean accepted = false;

    @Column(name = "responded",columnDefinition = "boolean")
    @NotNull
    private boolean responded = false;

    @Column(name = "returned")
    @NotNull
    private boolean returned = false;

    @Column(name = "share_date")
    @DateTimeFormat
    @NotNull
    private LocalDateTime shareDate = LocalDateTime.now();

    @Column(name = "return_date")
    @DateTimeFormat
    private LocalDateTime returnDate;

    //public Request(Long id) {this.item = new Item(id);}
}
