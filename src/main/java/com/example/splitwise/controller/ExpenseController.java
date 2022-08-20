package com.example.splitwise.controller;

import com.example.splitwise.dto.ExpenseDto;
import com.example.splitwise.dto.UserExpenseDto;
import com.example.splitwise.service.ExpenseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/expenses")
@Slf4j
public class ExpenseController {

    private final ExpenseService expenseService;
    private final LocalDateTime LOCAL_DATE_TIME_MIN = LocalDateTime.MIN;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<ExpenseDto> addExpense(@RequestBody ExpenseDto expenseDto) {
        try {
            expenseDto = expenseService.addExpense(expenseDto);

            log.info("Expense added successfully with id :: {}", expenseDto.getExpenseId());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(expenseDto);
        } catch (Exception e) {
            log.error("Failed to add expense due to {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<List<UserExpenseDto>> fetchExpenses(
            @RequestHeader @NotNull Long userId,
            @RequestParam(defaultValue = "-999999999-01-01") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(defaultValue = "+999999999-12-31") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) Long userGroupId) {
        try {
            List<UserExpenseDto> userExpenseDtoList;
            if (userGroupId == null)
                userExpenseDtoList = expenseService.fetchExpenses(userId, from.atStartOfDay(), to.atStartOfDay());
            else userExpenseDtoList = expenseService.fetchExpenses(userId, userGroupId);

            log.info("Successfully fetched {} expenses", userExpenseDtoList.size());
            return ResponseEntity.ok()
                    .body(userExpenseDtoList);
        } catch (Exception e) {
            log.error("Failed to fetch expenses due to {}", e.getMessage());
            throw e;
        }
    }
}
