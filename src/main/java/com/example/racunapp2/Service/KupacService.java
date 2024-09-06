package com.example.racunapp2.Service;

import com.example.racunapp2.Model.Kupac;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface KupacService {
    Optional<Kupac> findByEmail(String email);

    Kupac saveKupac(Kupac kupac);

    Optional<Kupac> findById(Integer id);

    boolean checkEmailAvailability(String email);
}
