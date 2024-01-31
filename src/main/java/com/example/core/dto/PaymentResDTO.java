package com.example.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class PaymentResDTO  implements Serializable {

    private String status;
    private String message;
    private String URL;
}
