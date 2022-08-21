package com.example.splitwise.splitter.impl;

import com.example.splitwise.dto.ExpenseDto;
import com.example.splitwise.dto.TransactionDto;
import com.example.splitwise.splitter.ExpenseSplitter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ExactExpenseSplitter implements ExpenseSplitter {

    @Override
    public List<TransactionDto> splitExpense(ExpenseDto expenseDto, Set<Long> userGroupUserIds) {
        this.validateExpense(expenseDto, userGroupUserIds);

        return expenseDto.getAmountDistributionMap().entrySet().stream()
                .filter(distEntry -> !distEntry.getValue().equals(0d))
                .filter(distEntry -> !distEntry.getKey().equals(expenseDto.getPaidByUserId()))
                .map(distEntry -> new TransactionDto(distEntry.getKey(), expenseDto.getPaidByUserId(), distEntry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public void validateExpense(ExpenseDto expenseDto, Set<Long> userGroupUserIds) {
        if (!userGroupUserIds.containsAll(expenseDto.getInvolvedUserIds())) {
            throw new IllegalArgumentException("Invalid input. Involved users do not belong to this user group");
        }
        if (!expenseDto.getInvolvedUserIds().contains(expenseDto.getPaidByUserId())) {
            throw new IllegalArgumentException("Invalid input. Involved users should include paid by user");
        }
        if (expenseDto.getAmountDistributionMap() == null || expenseDto.getAmountDistributionMap().isEmpty()) {
            throw new IllegalArgumentException("Invalid input. Amount distribution cannot be null for exact expense");
        }
        if (!expenseDto.getAmountDistributionMap().keySet().containsAll(expenseDto.getInvolvedUserIds())) {
            throw new IllegalArgumentException("Invalid input. Distribution info missing for few users");
        }
        Double totalAmt = expenseDto.getAmountDistributionMap().values().stream()
                .mapToDouble(d -> d).sum();
        if (!expenseDto.getAmount().equals(totalAmt)) {
            throw new IllegalArgumentException("Invalid input. Sum of distribution amt not matching with expense amt");
        }
    }
}
