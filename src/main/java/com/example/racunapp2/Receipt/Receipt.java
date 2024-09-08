package com.example.racunapp2.Receipt;

import com.example.racunapp2.Client.Client;
import com.example.racunapp2.Store.Store;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Racun")
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "ducan_id",nullable = true)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "kupac_id", nullable = true)
    @JsonBackReference
    private Client client;
    @JoinColumn(name = "datum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @JoinColumn(name = "ukupan_iznos")
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "racun", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<ItemReceipt> boughtItems;
    public Date getDate() {
        return date;
    }

    public void setDate(Date datum) {
        this.date = datum;
    }

    public Receipt() {
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal ukupan_iznos) {
        this.totalPrice = ukupan_iznos;
    }

    public Receipt(List<ItemReceipt> boughtItems) {
        this.boughtItems = boughtItems;
    }

    public BigDecimal getUkupnaCijena() {
        return totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public List<ItemReceipt> getBoughtItems() {
        return boughtItems;
    }

    public void setBoughtItems(List<ItemReceipt> boughtItems) {
        this.boughtItems = boughtItems;
    }
}
