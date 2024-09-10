package com.example.racunapp2.Client;

import com.example.racunapp2.Client.Client;
import com.example.racunapp2.Client.ClientService;
import com.example.racunapp2.Receipt.Receipt;
import com.example.racunapp2.Receipt.ReceiptRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

class ClientControllerTest {

    private ReceiptRepository receiptRepository = new ReceiptRepository() {
        @Override
        public List<Receipt> getAllByClientId(Integer id) {
            return null;
        }

        @Override
        public void flush() {

        }

        @Override
        public <S extends Receipt> S saveAndFlush(S entity) {
            return null;
        }

        @Override
        public <S extends Receipt> List<S> saveAllAndFlush(Iterable<S> entities) {
            return null;
        }

        @Override
        public void deleteAllInBatch(Iterable<Receipt> entities) {

        }

        @Override
        public void deleteAllByIdInBatch(Iterable<Integer> integers) {

        }

        @Override
        public void deleteAllInBatch() {

        }

        @Override
        public Receipt getOne(Integer integer) {
            return null;
        }

        @Override
        public Receipt getById(Integer integer) {
            return null;
        }

        @Override
        public Receipt getReferenceById(Integer integer) {
            return null;
        }

        @Override
        public <S extends Receipt> List<S> findAll(Example<S> example) {
            return null;
        }

        @Override
        public <S extends Receipt> List<S> findAll(Example<S> example, Sort sort) {
            return null;
        }

        @Override
        public <S extends Receipt> List<S> saveAll(Iterable<S> entities) {
            return null;
        }

        @Override
        public List<Receipt> findAll() {
            return null;
        }

        @Override
        public List<Receipt> findAllById(Iterable<Integer> integers) {
            return null;
        }

        @Override
        public <S extends Receipt> S save(S entity) {
            return null;
        }

        @Override
        public Optional<Receipt> findById(Integer integer) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(Integer integer) {
            return false;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void deleteById(Integer integer) {

        }

        @Override
        public void delete(Receipt entity) {

        }

        @Override
        public void deleteAllById(Iterable<? extends Integer> integers) {

        }

        @Override
        public void deleteAll(Iterable<? extends Receipt> entities) {

        }

        @Override
        public void deleteAll() {

        }

        @Override
        public List<Receipt> findAll(Sort sort) {
            return null;
        }

        @Override
        public Page<Receipt> findAll(Pageable pageable) {
            return null;
        }

        @Override
        public <S extends Receipt> Optional<S> findOne(Example<S> example) {
            return Optional.empty();
        }

        @Override
        public <S extends Receipt> Page<S> findAll(Example<S> example, Pageable pageable) {
            return null;
        }

        @Override
        public <S extends Receipt> long count(Example<S> example) {
            return 0;
        }

        @Override
        public <S extends Receipt> boolean exists(Example<S> example) {
            return false;
        }

        @Override
        public <S extends Receipt, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
            return null;
        }
    };

    private ClientService clientService = new ClientService() {
        @Override
        public Optional<Client> findByEmail(String email) {
            return Optional.empty();
        }

        @Override
        public Client saveClient(Client client) {
            // Return the client that was passed into the method
            return client;
        }

        @Override
        public Optional<Client> findById(Integer id) {
            return Optional.empty();
        }

        @Override
        public boolean checkEmailAvailability(String email) {
            return false;
        }
    };
    private ClientController clientController = new ClientController(clientService,receiptRepository);

    @Test
    void whenGetClientRegisterClient() {
        Client client = new Client(1, "client@email.com", "password");

        Client savedClient = clientService.saveClient(client);


        assertNotNull(savedClient);
        assertEquals(savedClient.getEmail(), client.getEmail());
        assertEquals(savedClient.getPassword(), client.getPassword());
    }

    @Test
    void getAccountsByEmail_receiptsFound() {
        Client clientId = new Client(1,"","");
        Receipt receipt1 = new Receipt();
        Receipt receipt2 = new Receipt();

        receipt1.setClient(clientId);
        receipt2.setClient(clientId);

        receiptRepository.save(receipt1);
        receiptRepository.save(receipt2);

        ResponseEntity<List<Receipt>> response = clientController.getAccountsByEmail(clientId.getId());

        // Assert that the response contains the correct data and status
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size()); // 2 receipts were saved
    }

    @Test
    void checkEmail() {
    }

    @Test
    void getReceiptsFromClient() {
    }
}
