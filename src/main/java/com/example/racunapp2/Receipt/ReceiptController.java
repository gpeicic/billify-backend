package com.example.racunapp2.Receipt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/racuni")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllReceipts() {
        List<Map<String, Object>> receipts = receiptService.getAllReceipts();
        return ResponseEntity.ok(receipts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getReceiptById(@PathVariable int id) {
        Map<String, Object> receiptData = receiptService.getReceiptDetailsById(id);
        if (!receiptData.isEmpty()) {
            return ResponseEntity.ok(receiptData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Receipt createReceipt(@RequestBody Receipt receipt) {
        return receiptService.saveReceipt(receipt);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Receipt> updateReceipt(@PathVariable int id, @RequestBody Receipt updatedReceipt) {
        Receipt receipt = receiptService.getReceiptById(id);
        if (receipt != null) {
            updatedReceipt.setId(id);
            receiptService.saveReceipt(updatedReceipt);
            return ResponseEntity.ok(updatedReceipt);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReceipt(@PathVariable int id) {
        if (receiptService.getReceiptById(id) != null) {
            receiptService.deleteReceipt(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
