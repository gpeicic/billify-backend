package com.example.racunapp2.Receipt;

import com.example.racunapp2.Item.Item;
import com.example.racunapp2.Receipt.Receipt;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "racunproizvod")
public class ItemReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "racun_id")
    @JsonBackReference
    private Receipt receipt;

    @ManyToOne
    @JoinColumn(name = "proizvod_id")
    private Item item;
    @Column(name = "kolicina")
    private BigDecimal amount;

    public ItemReceipt() {
    }

    public ItemReceipt(Receipt receipt, Item item, BigDecimal amount) {
        this.receipt = receipt;
        this.item = item;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Receipt getStore() {
        return receipt;
    }

    public void setStore(Receipt receipt) {
        this.receipt = receipt;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal kolicina) {
        this.amount = kolicina;
    }
}
