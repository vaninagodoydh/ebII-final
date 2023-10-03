package com.example.msusers.domain.models;

import lombok.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Bill {
    private String idBill;
    private String customerBill;
    private String productBill;
    private Double totalPrice;
}
