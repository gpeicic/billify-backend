package com.example.racunapp2.Controller;

import com.example.racunapp2.Model.Proizvod;
import com.example.racunapp2.Service.ProizvodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proizvodi")
public class ProizvodController {
    @Autowired
    private ProizvodService proizvodService;

    @GetMapping
    public List<Proizvod> getAllProizvodi() {
        return proizvodService.getAllProizvodi();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proizvod> getProizvodById(@PathVariable int id) {
        Proizvod proizvod = proizvodService.getProizvodById(id);
        if (proizvod != null) {
            return ResponseEntity.ok(proizvod);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Proizvod createProizvod(@RequestBody Proizvod proizvod) {
        return proizvodService.saveProizvod(proizvod);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proizvod> updateProizvod(@PathVariable int id, @RequestBody Proizvod updatedProizvod) {
        Proizvod proizvod = proizvodService.getProizvodById(id);
        if (proizvod != null) {
            updatedProizvod.setId(id);
            proizvodService.saveProizvod(updatedProizvod);
            return ResponseEntity.ok(updatedProizvod);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProizvod(@PathVariable int id) {
        if (proizvodService.getProizvodById(id) != null) {
            proizvodService.deleteProizvod(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
