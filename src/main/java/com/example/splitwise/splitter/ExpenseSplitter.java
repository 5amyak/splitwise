package com.example.splitwise.splitter;

import com.example.splitwise.dto.ExpenseDto;
import com.example.splitwise.dto.TransactionDto;

import java.util.List;

public interface ExpenseSplitter {

    List<TransactionDto> splitExpense(ExpenseDto expenseDto);

    void validateExpense(ExpenseDto expenseDto);
}
