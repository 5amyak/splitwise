package com.example.splitwise.splitter;

import com.example.splitwise.dto.SplitType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExpenseSplitterFactory {

    private final ExpenseSplitter equalExpenseSplitter;
    private final ExpenseSplitter exactExpenseSplitter;

    @Autowired
    public ExpenseSplitterFactory(ExpenseSplitter equalExpenseSplitter, ExpenseSplitter exactExpenseSplitter) {
        this.equalExpenseSplitter = equalExpenseSplitter;
        this.exactExpenseSplitter = exactExpenseSplitter;
    }

    public ExpenseSplitter getExpenseSplitter(SplitType splitType) {
        if (SplitType.EQUAL.equals(splitType)) return equalExpenseSplitter;
        else return exactExpenseSplitter;
    }
}
