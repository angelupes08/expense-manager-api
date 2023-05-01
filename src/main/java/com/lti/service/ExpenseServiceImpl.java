package com.lti.service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lti.dao.ExpenseRepo;
import com.lti.exceptions.ResourceNotFoundException;
import com.lti.model.Expense;

@Service
public class ExpenseServiceImpl implements ExpenseService {
	
	@Autowired
	private ExpenseRepo eRepo;
	
	@Autowired
	private UserService userService;

	@Override
	public Page<Expense> getExpense(Pageable page) {
		
		//Page<Expense> expense = eRepo.findAll(page);
		Page<Expense> expense = eRepo.findByUserId(userService.getLoggedInUser().getId(), page);
		return expense;
	}

	@Override
	public Expense getExpenseById(Long id) {
		
		//Optional<Expense> opt = eRepo.findById(id);
		Optional<Expense> opt = eRepo.findByUserIdAndId(userService.getLoggedInUser().getId(),id);
		if( opt.isPresent()) {
			return opt.get();
		}
		else 
			throw new ResourceNotFoundException("No expenses found for the id "+id);
	}

	@Override
	public void deleteExpenseById(Long id) {
		
		//eRepo.deleteById(id);
		Expense expense = getExpenseById(id);
		eRepo.delete(expense);
	
	}

	@Override
	public Expense saveExpense(Expense expense) {
		
		expense.setUser(userService.getLoggedInUser());
		Expense expenses = eRepo.save(expense);
		return expenses;
	}

	@Override
	public Expense updateExpense(Long id,Expense expense) {
		
		Expense existingExpense = getExpenseById(id);
		
		existingExpense.setName(expense.getName()!=null?expense.getName():existingExpense.getName());
		existingExpense.setCategory(expense.getCategory()!=null?expense.getCategory():existingExpense.getCategory());
		existingExpense.setAmount(expense.getAmount()!=null?expense.getAmount():existingExpense.getAmount());
		existingExpense.setDescription(expense.getDescription()!=null?expense.getDescription():existingExpense.getDescription());
		existingExpense.setDate(expense.getDate()!=null?expense.getDate():existingExpense.getDate());
		
		return eRepo.save(existingExpense);
		
	}

	@Override
	public List<Expense> readByCategory(String category, Pageable page) {
		
		List<Expense> e = eRepo.findByCategory(userService.getLoggedInUser().getId(),category, page).toList();
		return e;
	}

	@Override
	public List<Expense> readByName(String keyword, Pageable page) {
		
		List<Expense> e = eRepo.findByNameContaining(userService.getLoggedInUser().getId(),keyword, page).toList();
		return e;
	}

	@Override
	public List<Expense> readBydate(Date startDate, Date endDate, Pageable page) {
		
		if(startDate==null) {
			startDate = new Date(0);
		}
		if(endDate==null) {
			endDate = new Date(System.currentTimeMillis());
		}
		
		List<Expense> e = eRepo.findByDateBetween(userService.getLoggedInUser().getId(),startDate, endDate, page).toList();
		return e;
	}

}
