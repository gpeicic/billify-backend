package com.example.racunapp2.Client;

import com.example.racunapp2.Client.Client;
import com.example.racunapp2.Client.ClientService;
import com.example.racunapp2.Item.Item;
import com.example.racunapp2.Receipt.ItemReceipt;
import com.example.racunapp2.Receipt.Receipt;
import com.example.racunapp2.Receipt.ReceiptRepository;
import com.example.racunapp2.Store.Store;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
@WebMvcTest(ClientController.class)
@AutoConfigureMockMvc(addFilters = false)
class ClientControllerTest {



    @MockBean
    ClientService clientService;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ReceiptRepository receiptRepository;
    @Test
    void whenGetClientRegisterClient() throws Exception {
        Client client = new Client(1, "client@email.com", "password");

        when(clientService.saveClient(any(Client.class))).thenReturn(client);

        mockMvc.perform(post("/clients/register")  // Use POST here
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"email\":\"client@email.com\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(client.getEmail()));

    }

    @Test
    void getAccountsByEmail_receiptsFound() throws Exception {
        var client = new Client(1,"mail@gmail.com","password");
        var store = new Store(1,"Konzum","ilica 2");
        var item = new Item(1,"3928392","krastavac",BigDecimal.valueOf(2.99));

        var racuni =  List.of(new Receipt(1, store, client, new Date(), List.of(), BigDecimal.valueOf(100)));
        Mockito.when(receiptRepository.getAllByClientId(1)).thenReturn(racuni);
        MvcResult mvcResult = mockMvc.perform(get("/clients/1/accounts")).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void checkEmail_available() throws Exception {
        String email = "test@gmail.com";

        // Mock the service to return true (email is available)
        when(clientService.checkEmailAvailability(email)).thenReturn(true);

        mockMvc.perform(get("/clients/check")
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
    @Test
    void checkEmail_notAvailable() throws Exception {
        String email = "existing@gmail.com";

        // Mock the service to return false (email is not available)
        when(clientService.checkEmailAvailability(email)).thenReturn(false);

        mockMvc.perform(get("/clients/check")
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(content().string("false")); // Expect response to be "false"
    }

}
