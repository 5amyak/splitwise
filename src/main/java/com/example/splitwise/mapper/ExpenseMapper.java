package com.example.splitwise.mapper;

import com.example.splitwise.dto.ExpenseDto;
import com.example.splitwise.dto.TransactionDto;
import com.example.splitwise.dto.UserExpenseDto;
import com.example.splitwise.model.Expense;
import com.example.splitwise.model.Transaction;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ExpenseMapper {

    public static Expense toExpense(ExpenseDto expenseDto, ModelMapper modelMapper) {
        Expense expense = modelMapper.map(expenseDto, Expense.class);
        Set<Transaction> transactionSet = expenseDto.getTransactionDtoList()
                .stream()
                .map(transactionDto -> {
                    Transaction txn = modelMapper.map(transactionDto, Transaction.class);
                    txn.setExpense(expense);
                    return txn;
                })
                .collect(Collectors.toSet());
        expense.setTransactionSet(transactionSet);

        return expense;
    }

    public static ExpenseDto toExpenseDto(Expense expense, ModelMapper modelMapper) {
        ExpenseDto expenseDto = modelMapper.map(expense, ExpenseDto.class);
        List<TransactionDto> transactionDtoSet = expense.getTransactionSet()
                .stream()
                .map(txn -> modelMapper.map(txn, TransactionDto.class))
                .collect(Collectors.toList());
        expenseDto.setTransactionDtoList(transactionDtoSet);

        return expenseDto;
    }

    public static UserExpenseDto toUserExpenseDto(Expense e, Long userId) {

        return new UserExpenseDto()
                .setUserId(userId)
                .setExpenseDate(e.getDate().toLocalDate())
                .setExpenseTitle(e.getTitle())
                .setUserGroupTitle(e.getUserGroup().getTitle())
                .setExpenseAmount(e.getAmount())
                .setNetAmount(calculateNetAmount(e.getTransactionSet(), userId));
    }

    private static Double calculateNetAmount(Set<Transaction> transactionSet, Long userId) {
        Double netAmount = 0d;
        for (Transaction txn : transactionSet) {
            if (userId.equals(txn.getFromUser().getId())) netAmount -= txn.getTxnAmount();
            if (userId.equals(txn.getToUser().getId())) netAmount += txn.getTxnAmount();
        }

        return netAmount;
    }
}
