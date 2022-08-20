package com.example.splitwise.service;

import com.example.splitwise.dto.ExpenseDto;
import com.example.splitwise.dto.UserExpenseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseService {
    ExpenseDto addExpense(ExpenseDto expenseDto);

    List<UserExpenseDto> fetchExpenses(Long userId, LocalDateTime from, LocalDateTime to);

    List<UserExpenseDto> fetchExpenses(Long userId, Long userGroupId);

}
