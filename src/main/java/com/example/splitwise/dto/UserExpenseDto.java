package com.example.splitwise.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserExpenseDto {

    private Long userId;
    private LocalDate expenseDate;
    private String userGroupTitle;
    private String expenseTitle;
    private Double expenseAmount;
    private Double netAmount;
}
