package com.example.racunapp2.Client;

import java.util.Optional;

public interface ClientService {
    Optional<Client> findByEmail(String email);

    Client saveClient(Client client);

    Optional<Client> findById(Integer id);

    boolean checkEmailAvailability(String email);
}
