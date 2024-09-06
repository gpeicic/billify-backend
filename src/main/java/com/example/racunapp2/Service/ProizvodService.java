package com.example.racunapp2.Service;

import com.example.racunapp2.Model.Proizvod;
import com.example.racunapp2.Repository.ProizvodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProizvodService {
    @Autowired
    private ProizvodRepository proizvodRepository;

    public List<Proizvod> getAllProizvodi() {
        return proizvodRepository.findAll();
    }

    public Proizvod getProizvodById(int id) {
        return proizvodRepository.findById(id).orElse(null);
    }

    public Proizvod saveProizvod(Proizvod proizvod) {
        return proizvodRepository.save(proizvod);
    }

    public void deleteProizvod(int id) {
        proizvodRepository.deleteById(id);
    }
}
