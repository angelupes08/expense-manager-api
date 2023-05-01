package com.lti.controller;



import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lti.model.Expense;
import com.lti.service.ExpenseService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController
public class ExpenseController {
	
	@Autowired
	ExpenseService eService;
	
	@GetMapping("/expenses")
	public List<Expense> getExpenses(Pageable page) {
		
		List<Expense> expense = eService.getExpense(page).toList();
		return expense;
	}
	
	@GetMapping("/expense/{id}")
	public Expense getExpenseById(@PathVariable Long id) {
		
		return eService.getExpenseById(id);
		
	}
	
	@DeleteMapping("/deleteexpense")
	public String deleteExpense(@PathParam("id") Long id ) {
		
		eService.deleteExpenseById(id);		
		return "The expense has been deleted";
	}
	@ResponseStatus(value=HttpStatus.CREATED)
	@PostMapping("/expenses")
	public Expense saveExpenses(@Valid @RequestBody Expense expense) {
		
		
		return eService.saveExpense(expense);
		
	}
	
	@PutMapping("/updateexpenses/{id}")
	public Expense updateExpense(@RequestBody Expense expense, @PathVariable Long id) {
		
		Expense exp = eService.updateExpense(id, expense);
		return exp;
	}
	
	@GetMapping("expenses/category")
	public List<Expense> getExpenseByCategory(@RequestParam String category, Pageable page){
		return eService.readByCategory(category, page);
	}
	
	@GetMapping("expenses/name")
	public List<Expense> getExpenseByName(@RequestParam String keyword, Pageable page){
		return eService.readByName(keyword, page);
	}
	
	@GetMapping("expenses/date")
	public List<Expense> getExpenseByDate(@RequestParam(required = false) Date startDate,
											@RequestParam (required = false)Date endDate, Pageable page){
		return eService.readBydate(startDate, endDate, page);
	}

}
