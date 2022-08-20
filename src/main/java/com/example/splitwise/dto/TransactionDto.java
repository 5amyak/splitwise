package com.example.splitwise.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDto {

    private Long txnId;
    private Long fromUserId;
    private Long toUserId;
    private Double txnAmount;

    public TransactionDto(Long fromUserId, Long toUserId, Double txnAmount) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.txnAmount = txnAmount;
    }
}
