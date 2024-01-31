package com.example.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class PaymentDTO  implements Serializable {

    private Long idServicePack;
    private double amount;
    private String description;
    private String bankCode;
}
