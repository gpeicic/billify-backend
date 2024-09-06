package com.example.racunapp2.Controller;

import com.example.racunapp2.Model.Racun;
import com.example.racunapp2.Repository.RacunProizvodRepository;
import com.example.racunapp2.Repository.RacunRepository;
import com.example.racunapp2.Service.RacunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/racuni")
public class RacunController {

    @Autowired
    private RacunService racunService;

    @Autowired
    private RacunRepository racunRepository;

    @Autowired
    private RacunProizvodRepository racunProizvodRepository;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllRacuni() {
        List<Map<String, Object>> racuni = racunService.getAllRacuni();
        return ResponseEntity.ok(racuni);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getRacunById(@PathVariable int id) {
        Map<String, Object> racunData = racunService.getRacunDetailsById(id);
        if (!racunData.isEmpty()) {
            return ResponseEntity.ok(racunData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Racun createRacun(@RequestBody Racun racun) {
        return racunService.saveRacun(racun);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Racun> updateRacun(@PathVariable int id, @RequestBody Racun updatedRacun) {
        Racun racun = racunService.getRacunById(id);
        if (racun != null) {
            updatedRacun.setId(id);
            racunService.saveRacun(updatedRacun);
            return ResponseEntity.ok(updatedRacun);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRacun(@PathVariable int id) {
        if (racunService.getRacunById(id) != null) {
            racunService.deleteRacun(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
