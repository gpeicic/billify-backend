package com.example.racunapp2.Receipt;

import com.example.racunapp2.Store.Store;
import com.example.racunapp2.Item.Item;
import com.example.racunapp2.Store.StoreRepository;
import com.example.racunapp2.Item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ReceiptService {
    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private ItemReceiptRepository itemReceiptRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private StoreRepository storeRepository;
    public List<Map<String, Object>> getAllReceipts() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Receipt> receipts = receiptRepository.findAll();

        for (Receipt receipt : receipts) {
            Map<String, Object> receiptData = new HashMap<>();
            receiptData.put("racun_id", receipt.getId());
            receiptData.put("imeDucana", receipt.getStore().getStoreName());
            receiptData.put("adresa", receipt.getStore().getAddress());
            receiptData.put("datum", receipt.getDate());

            List<ItemReceipt> itemReceipts = itemReceiptRepository.findByReceipt(receipt);
            List<Map<String, Object>> itemsData = new ArrayList<>();

            BigDecimal totalPrice = BigDecimal.ZERO;

            for (ItemReceipt rp : itemReceipts) {
                Map<String, Object> itemData = new HashMap<>();

                String itemName = rp.getItem().getItemName();
                BigDecimal amount = rp.getAmount();
                BigDecimal pricePerUnit = rp.getItem().getPrice();

                BigDecimal totalPriceOfItem = pricePerUnit.multiply(amount).setScale(2, BigDecimal.ROUND_HALF_UP);

                itemData.put("imeProizvoda", itemName);
                itemData.put("kolicina", amount);
                itemData.put("cijena_po_jedinici", pricePerUnit);
                itemData.put("ukupna_cijena_proizvoda", totalPriceOfItem);

                itemsData.add(itemData);

                totalPrice = totalPrice.add(totalPriceOfItem);
            }

            receiptData.put("proizvodi", itemsData);
            receiptData.put("ukupna_cijena_racuna", totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
            result.add(receiptData);
        }

        return result;
    }
    public Receipt getReceiptById(int id) {
        return receiptRepository.findById(id).orElse(null);
    }
    public Map<String, Object> getReceiptDetailsById(int id) {
        Receipt receipt = getReceiptById(id);
        if (receipt == null) {
            return Collections.emptyMap();
        }

        Map<String, Object> receiptData = new HashMap<>();
        receiptData.put("racun_id", receipt.getId());
        receiptData.put("imeDucana", receipt.getStore().getStoreName());
        receiptData.put("adresa", receipt.getStore().getAddress());


        List<ItemReceipt> itemReceipts = itemReceiptRepository.findByReceipt(receipt);
        List<Map<String, Object>> itemsData = new ArrayList<>();

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (ItemReceipt rp : itemReceipts) {
            Map<String, Object> itemData = new HashMap<>();

            String itemName = rp.getItem().getItemName();
            BigDecimal amount = rp.getAmount();
            BigDecimal pricePerUnit = rp.getItem().getPrice();

            BigDecimal totalPriceOfItems = pricePerUnit.multiply(amount).setScale(2, BigDecimal.ROUND_HALF_UP);

            itemData.put("imeProizvoda", itemName);
            itemData.put("kolicina", amount);
            itemData.put("cijena_po_jedinici", pricePerUnit);
            itemData.put("ukupna_cijena_proizvoda", totalPriceOfItems);

            itemsData.add(itemData);

            totalPrice = totalPrice.add(totalPriceOfItems);
        }

        receiptData.put("proizvodi", itemsData);
        receiptData.put("ukupna_cijena_racuna", totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP));

        return receiptData;
    }

    public Receipt saveReceipt(Receipt receipt) {
        receipt.setDate(new Date());

        List<ItemReceipt> boughtItems = receipt.getBoughtItems();
        for (ItemReceipt rp : boughtItems) {
            Item p = itemRepository.findById(rp.getItem().getId())
                    .orElseThrow(() -> new RuntimeException("Proizvod s ID-om " + rp.getItem().getId() + " nije pronađen."));
            rp.setItem(p);
        }
        if (receipt.getStore() != null) {
            Store d = storeRepository.findById(receipt.getStore().getId())
                    .orElseThrow(() -> new RuntimeException("Dućan s ID-om " + receipt.getStore().getId() + " nije pronađen."));
            receipt.setStore(d);
        }

        BigDecimal totalPrice = receipt.getBoughtItems().stream()
                .map(itemReceipt -> {
                    BigDecimal pricePerUnit = itemReceipt.getItem().getPrice();
                    if (pricePerUnit == null) {
                        System.out.println("Upozorenje: Cijena po jedinici je null za proizvod ID: " + itemReceipt.getItem().getId());
                    }
                    BigDecimal amount = itemReceipt.getAmount();
                    return pricePerUnit != null ? pricePerUnit.multiply(amount) : BigDecimal.ZERO;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        receipt.setTotalPrice(totalPrice);

        return receiptRepository.save(receipt);
    }

    public void deleteReceipt(int id) {
        receiptRepository.deleteById(id);
    }
}
