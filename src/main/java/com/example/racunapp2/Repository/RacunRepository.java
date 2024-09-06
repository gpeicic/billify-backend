package com.example.racunapp2.Repository;

import com.example.racunapp2.Model.Racun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RacunRepository extends JpaRepository<Racun, Integer> {

    List<Racun> getAllByKupacId(Integer id);
}
