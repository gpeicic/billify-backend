package com.example.racunapp2.Client;

import com.example.racunapp2.Client.Client;
import com.example.racunapp2.Client.ClientRepository;
import com.example.racunapp2.Client.ClientService;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        return clientRepository.save(client);
    }

    public Optional<Client> findById(Integer id) {
        return clientRepository.findById(id);
    }

    public boolean checkEmailAvailability(String email) {
        return findByEmail(email).isEmpty();
    }

}
