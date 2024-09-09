package com.example.racunapp2.Config;

import com.example.racunapp2.Client.Client;
import com.example.racunapp2.Client.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;
@RestController
@RequestMapping("/google")
public class GoogleLoginController {
    private final GoogleTokenService googleTokenService;
    private final ClientService clientService;

    public GoogleLoginController(GoogleTokenService googleTokenService, ClientService clientService) {
        this.googleTokenService = googleTokenService;
        this.clientService = clientService;
    }

    @PostMapping("/googleLogin")
    public ResponseEntity<Client> googleLogin(@RequestBody Map<String, String> body) {
        try {
            String idToken = body.get("idToken");
            String email = googleTokenService.getEmailFromToken(idToken);
            Optional<Client> optionalClient = clientService.findByEmail(email);

            Client client;
            if (optionalClient.isPresent()) {
                client = optionalClient.get();
            } else {
                client = new Client();
                client.setEmail(email);
                client.setPassword(idToken);
                clientService.saveClient(client);
            }

            return ResponseEntity.ok(client);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(null);
        }
    }
}
