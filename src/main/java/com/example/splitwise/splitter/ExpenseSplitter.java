package com.example.splitwise.splitter;

import com.example.splitwise.dto.ExpenseDto;
import com.example.splitwise.dto.TransactionDto;

import java.util.List;
import java.util.Set;

public interface ExpenseSplitter {

    List<TransactionDto> splitExpense(ExpenseDto expenseDto, Set<Long> userGroupUserIds);

    void validateExpense(ExpenseDto expenseDto, Set<Long> userGroupUserIds);
}
