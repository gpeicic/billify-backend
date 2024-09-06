package com.example.racunapp2.Model;

import jakarta.persistence.*;





@Entity
@Table(name = "ducan")
public class Ducan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "imeducana")
    private String imeDucana;
    @Column(name = "adresa")
    private String adresa;


    public Ducan() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImeDucana() {
        return imeDucana;
    }

    public void setImeDucana(String imeDucana) {
        this.imeDucana = imeDucana;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }


    public Ducan(int id, String imeDucana, String adresa) {
        this.id = id;
        this.imeDucana = imeDucana;
        this.adresa = adresa;

    }


}
