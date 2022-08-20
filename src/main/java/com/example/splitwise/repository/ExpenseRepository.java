package com.example.splitwise.repository;

import com.example.splitwise.model.Expense;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Long> {

    @Query(value = "SELECT e.* " +
            "FROM expenses e, transactions t " +
            "WHERE t.expense_id = e.expense_id AND e.user_group_id=?2 " +
            "   AND (t.from_user_id=?1 OR t.to_user_id=?1)",
            nativeQuery = true)
    List<Expense> findExpensesByUserGroup(Long userId, Long userGroupId);

    @Query(value = "SELECT e.* " +
            "FROM expenses e, transactions t " +
            "WHERE t.expense_id = e.expense_id AND e.date>?2 AND e.date<?3 " +
            "   AND (t.from_user_id=?1 OR t.to_user_id=?1)",
            nativeQuery = true)
    List<Expense> findExpensesByDateAfterAndDateBefore(Long userId, LocalDateTime from, LocalDateTime to);
}