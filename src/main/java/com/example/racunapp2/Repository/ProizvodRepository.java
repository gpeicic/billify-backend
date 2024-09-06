package com.example.racunapp2.Repository;

import com.example.racunapp2.Model.Proizvod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProizvodRepository extends JpaRepository<Proizvod, Integer> {
}
