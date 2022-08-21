package com.example.splitwise.splitter.impl;

import com.example.splitwise.dto.ExpenseDto;
import com.example.splitwise.dto.TransactionDto;
import com.example.splitwise.splitter.ExpenseSplitter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EqualExpenseSplitter implements ExpenseSplitter {

    @Override
    public List<TransactionDto> splitExpense(ExpenseDto expenseDto, Set<Long> userGroupUserIds) {
        this.validateExpense(expenseDto, userGroupUserIds);
        Double equalSplitAmt = expenseDto.getAmount() / expenseDto.getInvolvedUserIds().size();

        return expenseDto.getInvolvedUserIds().stream()
                .filter(userId -> !userId.equals(expenseDto.getPaidByUserId()))
                .map(userId -> new TransactionDto(userId, expenseDto.getPaidByUserId(), equalSplitAmt))
                .collect(Collectors.toList());
    }

    @Override
    public void validateExpense(ExpenseDto expenseDto, Set<Long> userGroupUserIds) {
        if (!userGroupUserIds.containsAll(expenseDto.getInvolvedUserIds())) {
            throw new IllegalArgumentException("Invalid input. Involved users do not belong to this user group");
        }
        if (!expenseDto.getInvolvedUserIds().contains(expenseDto.getPaidByUserId())) {
            throw new IllegalArgumentException("Invalid Input. Involved users should include paid by user");
        }
    }

}
