package com.example.racunapp2.Client;

import com.example.racunapp2.Receipt.Receipt;
import com.example.racunapp2.Receipt.ReceiptRepository;
import com.example.racunapp2.Config.GoogleTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;
    private final ReceiptRepository receiptRepository;

    public ClientController(ClientService clientService, ReceiptRepository receiptRepository) {
        this.clientService = clientService;
        this.receiptRepository = receiptRepository;
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
    public ResponseEntity<List<Receipt>> getReceiptsFromClient(@PathVariable Integer id) {
        Optional<Client> client = clientService.findById(id);
        if (client.isPresent()) {
            List<Receipt> receipts = client.get().getReceipts();
            return ResponseEntity.ok(receipts);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
