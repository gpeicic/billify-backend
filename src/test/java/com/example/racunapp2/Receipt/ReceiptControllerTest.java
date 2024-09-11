package com.example.racunapp2.Receipt;

import com.example.racunapp2.Item.ItemController;
import com.example.racunapp2.Item.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(ReceiptController.class)
@AutoConfigureMockMvc(addFilters = false)
class ReceiptControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;
    @MockBean
    private ReceiptService receiptService;

    @MockBean
    private ReceiptRepository receiptRepository;

    @MockBean
    private ItemReceiptRepository itemReceiptRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllReceiptsShouldReturnReceipts() throws Exception {
        Receipt receipt1 = new Receipt();
        receipt1.setId(1);
        Receipt receipt2 = new Receipt();
        receipt2.setId(2);

        List<Map<String, Object>> receipts = Arrays.asList(
                Map.of("id", receipt1.getId()),
                Map.of("id", receipt2.getId())
        );

        when(receiptService.getAllReceipts()).thenReturn(receipts);

        mockMvc.perform(MockMvcRequestBuilders.get("/racuni")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andDo(print());
    }
    @Test
    void testGetReceiptByIdShouldReturnReceiptByID() throws Exception {
        Receipt receipt = new Receipt();
        receipt.setId(1);

        Map<String, Object> receiptData = new HashMap<>();
        receiptData.put("id", receipt.getId());

        when(receiptService.getReceiptDetailsById(anyInt())).thenReturn(receiptData);

        mockMvc.perform(MockMvcRequestBuilders.get("/racuni/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andDo(print());
    }
    @Test
    void testCreateReceiptShouldCreateReceipt() throws Exception {
        Receipt receipt = new Receipt();
        receipt.setId(1);

        when(receiptService.saveReceipt(any(Receipt.class))).thenReturn(receipt);

        mockMvc.perform(MockMvcRequestBuilders.post("/racuni")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}")) // Adjust this JSON to match your Receipt fields
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andDo(print());
    }
    @Test
    void testUpdateReceiptShouldUpdateReceipt() throws Exception {
        Receipt receipt = new Receipt();
        receipt.setId(1);

        when(receiptService.getReceiptById(anyInt())).thenReturn(receipt);
        when(receiptService.saveReceipt(any(Receipt.class))).thenReturn(receipt);

        mockMvc.perform(MockMvcRequestBuilders.put("/racuni/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}")) // Adjust this JSON to match your Receipt fields
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andDo(print());
    }

    @Test
    void testDeleteReceiptShouldDeleteReceipt() throws Exception {
        when(receiptService.getReceiptById(anyInt())).thenReturn(new Receipt());

        mockMvc.perform(MockMvcRequestBuilders.delete("/racuni/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(print());
    }
}