package com.example.racunapp2.Item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.*;
@WebMvcTest(ItemController.class)
@AutoConfigureMockMvc(addFilters = false)
class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllItemsShouldReturnItems() throws Exception {
        Item item1 = new Item(1,"293929", "Item1", BigDecimal.valueOf(10.21));
        Item item2 = new Item(2, "321312","Item2", BigDecimal.valueOf(10.99));

        when(itemService.getAllItems()).thenReturn(Arrays.asList(item1, item2));

        mockMvc.perform(get("/items")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].itemName").value("Item1"))
                .andExpect(jsonPath("$[1].itemName").value("Item2"));
    }
    @Test
    void testGetItemByIdShouldFoundItemById() throws Exception {
        Item item = new Item(1,"2838382", "Item1", BigDecimal.valueOf(3));

        when(itemService.getItemById(1)).thenReturn(item);

        mockMvc.perform(get("/items/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemName").value("Item1"))
                .andExpect(jsonPath("$.price").value(3));
    }
    @Test
    void testGetItemByIdShouldNotFound() throws Exception {
        when(itemService.getItemById(1)).thenReturn(null);

        mockMvc.perform(get("/items/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    void testCreateItemShouldCreateItem() throws Exception {
        Item item = new Item(1,"2938293819", "Item1",  BigDecimal.valueOf(3));

        when(itemService.saveItem(any(Item.class))).thenReturn(item);

        mockMvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Item1\",\"price\":3}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemName").value("Item1"))
                .andExpect(jsonPath("$.price").value(3));
    }
    @Test
    void testUpdateItemFoundShouldUpdateItem() throws Exception {
        Item existingItem = new Item(1,"74384", "Item1",  BigDecimal.valueOf(10.0));
        Item updatedItem = new Item(1,"2301831", "UpdatedItem",  BigDecimal.valueOf(15.0));

        when(itemService.getItemById(1)).thenReturn(existingItem);
        when(itemService.saveItem(any(Item.class))).thenReturn(updatedItem);

        mockMvc.perform(put("/items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"itemName\":\"UpdatedItem\",\"price\":15.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemName").value("UpdatedItem"))
                .andExpect(jsonPath("$.price").value(15.0));
    }
    @Test
    void testDeleteItemFound() throws Exception {
        when(itemService.getItemById(1)).thenReturn(new Item(1,"34298832", "Item1",  BigDecimal.valueOf(10.0)));

        mockMvc.perform(delete("/items/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(itemService).deleteItem(1);
    }

    @Test
    void testDeleteItemNotFound() throws Exception {
        when(itemService.getItemById(1)).thenReturn(null);

        mockMvc.perform(delete("/items/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(itemService, never()).deleteItem(anyInt());
    }
}