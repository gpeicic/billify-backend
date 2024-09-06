package com.example.racunapp2.Repository;

import com.example.racunapp2.Model.Racun;
import com.example.racunapp2.Model.RacunProizvod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RacunProizvodRepository extends JpaRepository<RacunProizvod, Long> {

    List<RacunProizvod> findByRacun(Racun racun);
}