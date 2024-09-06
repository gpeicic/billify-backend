package com.example.racunapp2.Controller;

import com.example.racunapp2.Model.Kupac;
import com.example.racunapp2.Model.Racun;
import com.example.racunapp2.Repository.RacunRepository;
import com.example.racunapp2.Service.GoogleTokenService;
import com.example.racunapp2.Service.KupacService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/kupci")
public class KupacController {

    private final KupacService kupacService;
    private final RacunRepository racunRepository;
    private final GoogleTokenService googleTokenService;

    public KupacController(KupacService kupacService, RacunRepository racunRepository, GoogleTokenService googleTokenService) {
        this.kupacService = kupacService;
        this.racunRepository = racunRepository;
        this.googleTokenService = googleTokenService;
    }

    @PostMapping("/googleLogin")
    public ResponseEntity<Kupac> googleLogin(@RequestBody Map<String, String> body) {
        try {
            String idToken = body.get("idToken");
            String email = googleTokenService.getEmailFromToken(idToken);
            Optional<Kupac> optionalKupac = kupacService.findByEmail(email);

            Kupac kupac;
            if (optionalKupac.isPresent()) {
                // Ako kupac postoji, koristimo ga
                kupac = optionalKupac.get();
            } else {
                // Ako kupac ne postoji, kreiramo novog kupca
                kupac = new Kupac();
                kupac.setEmail(email);
                kupac.setLozinka(idToken); // Postavljanje ID tokena kao lozinke
                kupacService.saveKupac(kupac);
            }

            return ResponseEntity.ok(kupac);
        } catch (Exception e) {
            e.printStackTrace();
            // U slučaju greške, vraća status 400
            return ResponseEntity.status(400).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Kupac> registerKupac(@RequestBody Kupac kupac) {

        //   kupac.setLozinka(passwordEncoder.encode(kupac.getLozinka()));
        return ResponseEntity.ok(kupacService.saveKupac(kupac));

    }

    @GetMapping("/{id}/accounts")
    public ResponseEntity<List<Racun>> getAccountsByEmail(@PathVariable Integer id) {
        List<Racun> racuni = racunRepository.getAllByKupacId(id);

        if (racuni != null) {
            return ResponseEntity.ok(racuni);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/check")
    public boolean checkEmail(@RequestParam String email) {
        return kupacService.checkEmailAvailability(email);
    }

    @GetMapping("/{id}/racuni")
    public ResponseEntity<List<Racun>> getRacuniByKupac(@PathVariable Integer id) {
        Optional<Kupac> kupac = kupacService.findById(id);
        if (kupac.isPresent()) {
            List<Racun> racuni = kupac.get().getRacuni();
            return ResponseEntity.ok(racuni);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
