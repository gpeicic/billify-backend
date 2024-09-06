package com.example.racunapp2.Service;

import com.example.racunapp2.Model.Ducan;
import com.example.racunapp2.Model.Proizvod;
import com.example.racunapp2.Model.Racun;
import com.example.racunapp2.Model.RacunProizvod;
import com.example.racunapp2.Repository.DucanRepository;
import com.example.racunapp2.Repository.ProizvodRepository;
import com.example.racunapp2.Repository.RacunProizvodRepository;
import com.example.racunapp2.Repository.RacunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class RacunService {
    @Autowired
    private RacunRepository racunRepository;
    @Autowired
    private RacunProizvodRepository racunProizvodRepository;
    @Autowired
    private ProizvodRepository proizvodRepository;

    @Autowired
    private DucanRepository ducanRepository;
    public List<Map<String, Object>> getAllRacuni() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Racun> racuni = racunRepository.findAll();

        for (Racun racun : racuni) {
            Map<String, Object> racunData = new HashMap<>();
            racunData.put("racun_id", racun.getId());
            racunData.put("imeDucana", racun.getDucan().getImeDucana());
            racunData.put("adresa", racun.getDucan().getAdresa());
            racunData.put("datum", racun.getDatum());

            List<RacunProizvod> racunProizvodi = racunProizvodRepository.findByRacun(racun);
            List<Map<String, Object>> proizvodiData = new ArrayList<>();

            BigDecimal ukupnaCijenaRacuna = BigDecimal.ZERO;

            for (RacunProizvod rp : racunProizvodi) {
                Map<String, Object> proizvodData = new HashMap<>();

                String imeProizvoda = rp.getProizvod().getImeProizvoda();
                BigDecimal kolicina = rp.getKolicina();
                BigDecimal cijenaPoJednici = rp.getProizvod().getCijena();

                BigDecimal ukupnaCijenaProizvoda = cijenaPoJednici.multiply(kolicina).setScale(2, BigDecimal.ROUND_HALF_UP);

                proizvodData.put("imeProizvoda", imeProizvoda);
                proizvodData.put("kolicina", kolicina);
                proizvodData.put("cijena_po_jedinici", cijenaPoJednici);
                proizvodData.put("ukupna_cijena_proizvoda", ukupnaCijenaProizvoda);

                proizvodiData.add(proizvodData);

                ukupnaCijenaRacuna = ukupnaCijenaRacuna.add(ukupnaCijenaProizvoda);
            }

            racunData.put("proizvodi", proizvodiData);
            racunData.put("ukupna_cijena_racuna", ukupnaCijenaRacuna.setScale(2, BigDecimal.ROUND_HALF_UP));
            result.add(racunData);
        }

        return result;
    }
    public Racun getRacunById(int id) {
        return racunRepository.findById(id).orElse(null);
    }
    public Map<String, Object> getRacunDetailsById(int id) {
        Racun racun = getRacunById(id);
        if (racun == null) {
            return Collections.emptyMap();
        }

        Map<String, Object> racunData = new HashMap<>();
        racunData.put("racun_id", racun.getId());
        racunData.put("imeDucana", racun.getDucan().getImeDucana());
        racunData.put("adresa", racun.getDucan().getAdresa());


        List<RacunProizvod> racunProizvodi = racunProizvodRepository.findByRacun(racun);
        List<Map<String, Object>> proizvodiData = new ArrayList<>();

        BigDecimal ukupnaCijenaRacuna = BigDecimal.ZERO;

        for (RacunProizvod rp : racunProizvodi) {
            Map<String, Object> proizvodData = new HashMap<>();

            String imeProizvoda = rp.getProizvod().getImeProizvoda();
            BigDecimal kolicina = rp.getKolicina();
            BigDecimal cijenaPoJednici = rp.getProizvod().getCijena();

            BigDecimal ukupnaCijenaProizvoda = cijenaPoJednici.multiply(kolicina).setScale(2, BigDecimal.ROUND_HALF_UP);

            proizvodData.put("imeProizvoda", imeProizvoda);
            proizvodData.put("kolicina", kolicina);
            proizvodData.put("cijena_po_jedinici", cijenaPoJednici);
            proizvodData.put("ukupna_cijena_proizvoda", ukupnaCijenaProizvoda);

            proizvodiData.add(proizvodData);

            ukupnaCijenaRacuna = ukupnaCijenaRacuna.add(ukupnaCijenaProizvoda);
        }

        racunData.put("proizvodi", proizvodiData);
        racunData.put("ukupna_cijena_racuna", ukupnaCijenaRacuna.setScale(2, BigDecimal.ROUND_HALF_UP));

        return racunData;
    }

    public Racun saveRacun(Racun racun) {
        // Postavi datum na trenutni datum
        racun.setDatum(new Date());

        // Dohvati sve proizvode iz baze podataka prema ID-evima u racun.getKupljeniProizvodi()
        List<RacunProizvod> kupljeniProizvodi = racun.getKupljeniProizvodi();
        for (RacunProizvod rp : kupljeniProizvodi) {
            Proizvod p = proizvodRepository.findById(rp.getProizvod().getId())
                    .orElseThrow(() -> new RuntimeException("Proizvod s ID-om " + rp.getProizvod().getId() + " nije pronađen."));
            rp.setProizvod(p);  // Postavi kompletan proizvod
        }

        // Dohvati dućan iz baze podataka
        if (racun.getDucan() != null) {
            Ducan d = ducanRepository.findById(racun.getDucan().getId())
                    .orElseThrow(() -> new RuntimeException("Dućan s ID-om " + racun.getDucan().getId() + " nije pronađen."));
            racun.setDucan(d);  // Postavi kompletan dućan
        }

        // Ispis svih proizvoda u računu za debugiranje
        System.out.println("Proizvodi u računu:");
        for (RacunProizvod proizvod : racun.getKupljeniProizvodi()) {
            Proizvod p = proizvod.getProizvod();
            System.out.println("Proizvod: " + p.getId());
            System.out.println("Cijena: " + p.getCijena());
            System.out.println("Količina: " + proizvod.getKolicina());
        }

        // Ispis dućana za debugiranje
        System.out.println("Dućan u računu:");
        if (racun.getDucan() != null) {
            Ducan d = racun.getDucan();
            System.out.println("Dućan ID: " + d.getId());
            System.out.println("Ime dućana: " + d.getImeDucana());
            System.out.println("Adresa: " + d.getAdresa());
        }

        // Izračunaj ukupnu cijenu
        BigDecimal ukupnaCijena = racun.getKupljeniProizvodi().stream()
                .map(racunProizvod -> {
                    BigDecimal cijenaPoJedinici = racunProizvod.getProizvod().getCijena();
                    if (cijenaPoJedinici == null) {
                        System.out.println("Upozorenje: Cijena po jedinici je null za proizvod ID: " + racunProizvod.getProizvod().getId());
                    }
                    BigDecimal kolicina = racunProizvod.getKolicina();
                    return cijenaPoJedinici != null ? cijenaPoJedinici.multiply(kolicina) : BigDecimal.ZERO;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        racun.setUkupan_iznos(ukupnaCijena);

        // Spremi i vrati račun
        return racunRepository.save(racun);
    }

    public void deleteRacun(int id) {
        racunRepository.deleteById(id);
    }
}
