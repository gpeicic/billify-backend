package com.example.racunapp2.Receipt;

import com.example.racunapp2.Item.Item;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "itemreceipt")
public class ItemReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "receipt_id")
    @JsonBackReference
    private Receipt receipt;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    @Column(name = "amount")
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

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
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

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
