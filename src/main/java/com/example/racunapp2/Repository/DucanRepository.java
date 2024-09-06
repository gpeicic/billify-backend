package com.example.racunapp2.Repository;

import com.example.racunapp2.Model.Ducan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DucanRepository extends JpaRepository<Ducan, Integer> {
    // Ovdje možete dodati dodatne metode ako su potrebne
}