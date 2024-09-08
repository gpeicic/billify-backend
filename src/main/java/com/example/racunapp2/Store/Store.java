package com.example.racunapp2.Store;

import jakarta.persistence.*;





@Entity
@Table(name = "ducan")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "imeducana")
    private String storeName;
    @Column(name = "adresa")
    private String address;


    public Store() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String imeDucana) {
        this.storeName = imeDucana;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String adresa) {
        this.address = adresa;
    }


    public Store(int id, String storeName, String address) {
        this.id = id;
        this.storeName = storeName;
        this.address = address;

    }


}
