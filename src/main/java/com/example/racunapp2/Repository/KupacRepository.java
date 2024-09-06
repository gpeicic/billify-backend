package com.example.racunapp2.Repository;

import com.example.racunapp2.Model.Kupac;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface KupacRepository extends JpaRepository<Kupac, Integer> {

    Optional<Kupac> findByEmail(String email);


}
