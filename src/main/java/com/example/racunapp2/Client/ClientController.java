package com.example.racunapp2.Client;

import com.example.racunapp2.Receipt.Receipt;
import com.example.racunapp2.Receipt.ReceiptRepository;
import com.example.racunapp2.Service.GoogleTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/kupci")
public class ClientController {

    private final ClientService clientService;
    private final ReceiptRepository receiptRepository;
    private final GoogleTokenService googleTokenService;

    public ClientController(ClientService clientService, ReceiptRepository receiptRepository, GoogleTokenService googleTokenService) {
        this.clientService = clientService;
        this.receiptRepository = receiptRepository;
        this.googleTokenService = googleTokenService;
    }

    @PostMapping("/googleLogin")
    public ResponseEntity<Client> googleLogin(@RequestBody Map<String, String> body) {
        try {
            String idToken = body.get("idToken");
            String email = googleTokenService.getEmailFromToken(idToken);
            Optional<Client> optionalClient = clientService.findByEmail(email);

            Client client;
            if (optionalClient.isPresent()) {
                // Ako kupac postoji, koristimo ga
                client = optionalClient.get();
            } else {
                // Ako kupac ne postoji, kreiramo novog kupca
                client = new Client();
                client.setEmail(email);
                client.setPassword(idToken); // Postavljanje ID tokena kao lozinke
                clientService.saveClient(client);
            }

            return ResponseEntity.ok(client);
        } catch (Exception e) {
            e.printStackTrace();
            // U slučaju greške, vraća status 400
            return ResponseEntity.status(400).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Client> registerClient(@RequestBody Client client) {

        //   kupac.setLozinka(passwordEncoder.encode(kupac.getLozinka()));
        return ResponseEntity.ok(clientService.saveClient(client));

    }

    @GetMapping("/{id}/accounts")
    public ResponseEntity<List<Receipt>> getAccountsByEmail(@PathVariable Integer id) {
        List<Receipt> receipts = receiptRepository.getAllByClientId(id);

        if (receipts != null) {
            return ResponseEntity.ok(receipts);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/check")
    public boolean checkEmail(@RequestParam String email) {
        return clientService.checkEmailAvailability(email);
    }

    @GetMapping("/{id}/racuni")
    public ResponseEntity<List<Receipt>> getReceiptByClient(@PathVariable Integer id) {
        Optional<Client> client = clientService.findById(id);
        if (client.isPresent()) {
            List<Receipt> racuni = client.get().getReceipt();
            return ResponseEntity.ok(racuni);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
