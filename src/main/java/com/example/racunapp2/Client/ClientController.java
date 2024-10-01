package com.example.racunapp2.Client;

import com.example.racunapp2.Receipt.Receipt;
import com.example.racunapp2.Receipt.ReceiptRepository;
import com.example.racunapp2.Config.GoogleTokenService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> registerClient(@RequestBody Client client) {
        try {
            clientService.saveClient(client);
            return ResponseEntity.ok("Registration successful!");
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        }

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
    @GetMapping("/{id}/email")
    public ResponseEntity<String> getEmailById(@PathVariable Integer id) {
        Optional<Client> client = clientService.findById(id);

        if (client.isPresent()) {
            return ResponseEntity.ok(client.get().getEmail());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/check")
    public boolean checkEmail(@RequestParam String email) {
        return clientService.checkEmailAvailability(email);
    }

}
