package com.example.racunapp2.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "proizvod")
public class Proizvod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "serijskibroj")
    private String serijskiBroj;
    @Column(name = "imeproizvoda")
    private String imeProizvoda;
    @Column(name = "cijena")
    private BigDecimal cijena;

    @OneToMany(mappedBy = "proizvod",fetch = FetchType.EAGER)
    @JsonIgnore
    private List<RacunProizvod> racuniProizvoda;

    public Proizvod() {
    }

    public Proizvod(int id, String serijskiBroj, String imeProizvoda, BigDecimal cijena) {
        this.id = id;
        this.serijskiBroj = serijskiBroj;
        this.imeProizvoda = imeProizvoda;
        this.cijena = cijena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerijskiBroj() {
        return serijskiBroj;
    }

    public void setSerijskiBroj(String serijskiBroj) {
        this.serijskiBroj = serijskiBroj;
    }

    public String getImeProizvoda() {
        return imeProizvoda;
    }

    public void setImeProizvoda(String imeProizvoda) {
        this.imeProizvoda = imeProizvoda;
    }

    public BigDecimal getCijena() {
        return cijena;
    }

    public void setCijena(BigDecimal cijena) {
        this.cijena = cijena;
    }

    public List<RacunProizvod> getRacuniProizvoda() {
        return racuniProizvoda;
    }

    public void setRacuniProizvoda(List<RacunProizvod> racuniProizvoda) {
        this.racuniProizvoda = racuniProizvoda;
    }
}
