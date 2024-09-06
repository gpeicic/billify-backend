package com.example.racunapp2.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "racunproizvod")
public class RacunProizvod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "racun_id")
    @JsonBackReference
    private Racun racun;

    @ManyToOne
    @JoinColumn(name = "proizvod_id")
    private Proizvod proizvod;
    @Column(name = "kolicina")
    private BigDecimal kolicina;

    public RacunProizvod() {
    }

    public RacunProizvod(Racun racun, Proizvod proizvod, BigDecimal kolicina) {
        this.racun = racun;
        this.proizvod = proizvod;
        this.kolicina = kolicina;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Racun getRacun() {
        return racun;
    }

    public void setRacun(Racun racun) {
        this.racun = racun;
    }

    public Proizvod getProizvod() {
        return proizvod;
    }

    public void setProizvod(Proizvod proizvod) {
        this.proizvod = proizvod;
    }

    public BigDecimal getKolicina() {
        return kolicina;
    }

    public void setKolicina(BigDecimal kolicina) {
        this.kolicina = kolicina;
    }
}
