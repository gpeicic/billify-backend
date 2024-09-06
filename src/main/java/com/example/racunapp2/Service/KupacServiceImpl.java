package com.example.racunapp2.Service;

import com.example.racunapp2.Model.Kupac;
import com.example.racunapp2.Repository.KupacRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KupacServiceImpl implements KupacService {
    private final KupacRepository kupacRepository;

    public KupacServiceImpl(KupacRepository kupacRepository) {
        this.kupacRepository = kupacRepository;
    }

    public Optional<Kupac> findByEmail(String email) {
        return kupacRepository.findByEmail(email);
    }

    public Kupac saveKupac(Kupac kupac) {
        return kupacRepository.save(kupac);
    }

    public Optional<Kupac> findById(Integer id) {
        return kupacRepository.findById(id);
    }

    public boolean checkEmailAvailability(String email) {
        return findByEmail(email).isEmpty();
    }

}
