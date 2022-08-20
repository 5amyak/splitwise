package com.example.splitwise.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpenseDto {

    private Long expenseId;

    @NotNull
    private Long userGroupId;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @Min(0)
    private Double amount;

    @NotNull
    private SplitType splitType;

    @NotNull
    private Long paidByUserId;

    @NotEmpty
    @JsonProperty("involvedUsers")
    private List<Long> involvedUserIds;

    private Map<Long, Double> amountDistributionMap;

    @JsonProperty("transactions")
    private List<TransactionDto> transactionDtoList;

    private LocalDateTime date = LocalDateTime.now();


}
