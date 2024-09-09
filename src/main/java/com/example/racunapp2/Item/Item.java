package com.example.racunapp2.Item;

import com.example.racunapp2.Receipt.ItemReceipt;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "serialnumber")
    private String serialNumber;
    @Column(name = "itemname")
    private String itemName;
    @Column(name = "price")
    private BigDecimal price;

    @OneToMany(mappedBy = "item",fetch = FetchType.EAGER)
    @JsonIgnore
    private List<ItemReceipt> itemReceipts;

    public Item() {
    }

    public Item(int id, String serialNumber, String itemName, BigDecimal price) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.itemName = itemName;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<ItemReceipt> getItemReceipts() {
        return itemReceipts;
    }

    public void setItemReceipts(List<ItemReceipt> itemReceipts) {
        this.itemReceipts = itemReceipts;
    }
}
