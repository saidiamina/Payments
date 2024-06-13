package com.example.payments.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    public enum PaymentType {
        CREDIT_CARD, GIFT_CARD, PAYPAL
    }

    public enum Status {
        NEW, AUTHORISED, CAPTURED, CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Item> items;

    public Transaction(double amount, PaymentType paymentType, List<Item> items) {
        this.amount = amount;
        this.paymentType = paymentType;
        this.items = items;
        this.status = Status.NEW;
    }
}
