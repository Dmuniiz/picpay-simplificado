package com.picpaysimplificado.DTO;

import java.math.BigDecimal;

public record TransactionDTO (BigDecimal amount, Long senderId, Long receiverId){
}
