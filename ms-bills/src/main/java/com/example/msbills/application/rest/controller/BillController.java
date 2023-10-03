package com.example.msbills.application.rest.controller;

import com.example.msbills.domain.models.Bill;
import com.example.msbills.infrastructure.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bills")
@RequiredArgsConstructor
public class BillController {
    private final BillService service;

    @GetMapping("/all")
    public ResponseEntity<List<Bill>> getAll() {
        return ResponseEntity.ok().body(service.getAllBill());
    }

    @GetMapping("/perUser/{userId}")
    public ResponseEntity<List<Bill>> getAlBillsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok().body(service.getAllBillsByUserId(userId));
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('GROUP_PROVIDERS')")
    public ResponseEntity<Bill> createNew(@RequestBody Bill bill) {
        return ResponseEntity.ok().body(service.createNewBill(bill));
    }

}
