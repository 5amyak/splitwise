package com.example.splitwise.splitter;

import com.example.splitwise.dto.ExpenseDto;
import com.example.splitwise.dto.TransactionDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExactExpenseSplitter implements ExpenseSplitter {

    @Override
    public List<TransactionDto> splitExpense(ExpenseDto expenseDto) {
        this.validateExpense(expenseDto);

        return expenseDto.getAmountDistributionMap().entrySet().stream()
                .filter(distEntry -> !distEntry.getValue().equals(0d))
                .filter(distEntry -> !distEntry.getKey().equals(expenseDto.getPaidByUserId()))
                .map(distEntry -> new TransactionDto(distEntry.getKey(), expenseDto.getPaidByUserId(), distEntry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public void validateExpense(ExpenseDto expenseDto) {
        if (!expenseDto.getInvolvedUserIds().contains(expenseDto.getPaidByUserId())) {
            throw new IllegalArgumentException("Invalid input. Involved users should include paid by user");
        }
        if (expenseDto.getAmountDistributionMap() == null || expenseDto.getAmountDistributionMap().isEmpty()) {
            throw new IllegalArgumentException("Invalid input. Amount distribution cannot be null for exact expense");
        }
        if (expenseDto.getAmountDistributionMap().size() != expenseDto.getInvolvedUserIds().size()) {
            throw new IllegalArgumentException("Invalid input. Distribution info missing for few users");
        }
        Double totalAmt = expenseDto.getAmountDistributionMap().values().stream()
                .mapToDouble(d -> d).sum();
        if (!expenseDto.getAmount().equals(totalAmt)) {
            throw new IllegalArgumentException("Invalid input. Sum of distribution amt not matching with expense amt");
        }
    }
}
