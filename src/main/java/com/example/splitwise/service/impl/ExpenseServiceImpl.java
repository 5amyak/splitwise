package com.example.splitwise.service.impl;

import com.example.splitwise.dto.ExpenseDto;
import com.example.splitwise.dto.TransactionDto;
import com.example.splitwise.dto.UserExpenseDto;
import com.example.splitwise.mapper.ExpenseMapper;
import com.example.splitwise.model.Expense;
import com.example.splitwise.model.User;
import com.example.splitwise.model.UserGroup;
import com.example.splitwise.repository.ExpenseRepository;
import com.example.splitwise.repository.UserGroupRepository;
import com.example.splitwise.service.ExpenseService;
import com.example.splitwise.splitter.ExpenseSplitter;
import com.example.splitwise.splitter.ExpenseSplitterFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserGroupRepository userGroupRepository;
    private final ExpenseSplitterFactory expenseSplitterFactory;
    private final ModelMapper modelMapper;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository, UserGroupRepository userGroupRepository,
                              ExpenseSplitterFactory expenseSplitterFactory, ModelMapper modelMapper) {
        this.expenseRepository = expenseRepository;
        this.userGroupRepository = userGroupRepository;
        this.expenseSplitterFactory = expenseSplitterFactory;
        this.modelMapper = modelMapper;
    }

    @Override
    public ExpenseDto addExpense(ExpenseDto expenseDto) {
        Set<Long> userGroupUserIds = userGroupRepository.findById(expenseDto.getUserGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid input. User group id does not exist"))
                .getUsers().stream()
                .map(User::getId).collect(Collectors.toSet());
        ExpenseSplitter expenseSplitter = expenseSplitterFactory.getExpenseSplitter(expenseDto.getSplitType());
        List<TransactionDto> transactionDtoList = expenseSplitter.splitExpense(expenseDto, userGroupUserIds);
        expenseDto.setTransactionDtoList(transactionDtoList);

        Expense expense = ExpenseMapper.toExpense(expenseDto, modelMapper);
        expense = expenseRepository.save(expense);
        return ExpenseMapper.toExpenseDto(expense, modelMapper);
    }

    @Override
    public List<UserExpenseDto> fetchExpenses(Long userId, LocalDateTime from, LocalDateTime to) {
        List<Expense> expenseList = expenseRepository.findExpensesByDateAfterAndDateBefore(userId, from, to);

        return expenseList.stream()
                .map(e -> ExpenseMapper.toUserExpenseDto(e, userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserExpenseDto> fetchExpenses(Long userId, Long userGroupId) {
        UserGroup userGroup = new UserGroup();
        userGroup.setId(userGroupId);
        List<Expense> expenseList = expenseRepository.findExpensesByUserGroup(userId, userGroupId);

        return expenseList.stream()
                .map(e -> ExpenseMapper.toUserExpenseDto(e, userId))
                .collect(Collectors.toList());
    }
}
