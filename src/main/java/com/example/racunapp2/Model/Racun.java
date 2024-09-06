package com.example.racunapp2.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Racun")
public class Racun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "ducan_id",nullable = true)
    private Ducan ducan;

    @ManyToOne
    @JoinColumn(name = "kupac_id", nullable = true)
    @JsonBackReference
    private Kupac kupac;
    @JoinColumn(name = "datum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datum;


    @OneToMany(mappedBy = "racun", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<RacunProizvod> kupljeniProizvodi;
    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }


    @JoinColumn(name = "ukupan_iznos")
    private BigDecimal ukupan_iznos;

    public Racun() {
    }

    public BigDecimal getUkupan_iznos() {
        return ukupan_iznos;
    }

    public void setUkupan_iznos(BigDecimal ukupan_iznos) {
        this.ukupan_iznos = ukupan_iznos;
    }

    public Racun(List<RacunProizvod> kupljeniProizvodi) {
        this.kupljeniProizvodi = kupljeniProizvodi;
    }

    public BigDecimal getUkupnaCijena() {
        return ukupan_iznos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ducan getDucan() {
        return ducan;
    }

    public void setDucan(Ducan ducan) {
        this.ducan = ducan;
    }

    public List<RacunProizvod> getKupljeniProizvodi() {
        return kupljeniProizvodi;
    }

    public void setKupljeniProizvodi(List<RacunProizvod> kupljeniProizvodi) {
        this.kupljeniProizvodi = kupljeniProizvodi;
    }
}
