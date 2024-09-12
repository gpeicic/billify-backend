package com.example.racunapp2.Client;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Optional<Client> findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public Client saveClient(Client client) {
        // Check email validity before saving
        if (!isValidEmail(client.getEmail())) {
            throw new IllegalArgumentException("Invalid email address");
        }
        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists: " + client.getEmail());
        }

        return clientRepository.save(client);
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return email != null && pattern.matcher(email).matches();
    }

    public Optional<Client> findById(Integer id) {
        return clientRepository.findById(id);
    }

    public boolean checkEmailAvailability(String email) {
        return findByEmail(email).isEmpty();
    }

}
