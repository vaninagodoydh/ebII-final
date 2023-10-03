package com.example.msbills.infrastructure.service;

import com.example.msbills.domain.models.Bill;
import com.example.msbills.domain.repositories.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository repository;

    public List<Bill> getAllBill() {
        return repository.findAll();
    }
    public List<Bill> getAllBillsByUserId(String userId) {
        return  repository.findByCustomerId(userId);
    }
    public Bill createNewBill(Bill bill) {
        return repository.save(bill);
    }

}
