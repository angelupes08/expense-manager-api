package com.lti.service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lti.model.Expense;

public interface ExpenseService {
	
	Page<Expense> getExpense(Pageable page);
	
	Expense getExpenseById(Long id);
	
	void deleteExpenseById(Long id);
	
	Expense saveExpense(Expense expense);
	
	Expense updateExpense(Long id,Expense expense);
	
	List<Expense> readByCategory(String category, Pageable page);
	
	List<Expense> readByName(String keyword, Pageable page);
	
	List<Expense> readBydate(Date startdate, Date endDate, Pageable page);
}
