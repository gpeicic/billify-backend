package com.example.racunapp2.Item;

import com.example.racunapp2.Receipt.ItemReceipt;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "proizvod")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "serijskibroj")
    private String serialNumber;
    @Column(name = "imeproizvoda")
    private String itemName;
    @Column(name = "cijena")
    private BigDecimal price;

    @OneToMany(mappedBy = "proizvod",fetch = FetchType.EAGER)
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

    public void setSerialNumber(String serijskiBroj) {
        this.serialNumber = serijskiBroj;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String imeProizvoda) {
        this.itemName = imeProizvoda;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal cijena) {
        this.price = cijena;
    }

    public List<ItemReceipt> getItemReceipts() {
        return itemReceipts;
    }

    public void setItemReceipts(List<ItemReceipt> racuniProizvoda) {
        this.itemReceipts = racuniProizvoda;
    }
}
