package com.example.racunapp2.Receipt;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemReceiptRepository extends JpaRepository<ItemReceipt, Long> {

    List<ItemReceipt> findByReceipt(Receipt receipt);
}