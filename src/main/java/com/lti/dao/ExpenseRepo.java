package com.lti.dao;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lti.model.Expense;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense, Long> {
	
	//SELECT * FROM EXPENSES WHERE USERID =? and CATEGORY=?
	Page<Expense> findByCategory(Long userId,String category,Pageable page);
	
	//SELECT * FROM EXPENSES WHERE USERID =? and NAME LIKE '%keyword%'
	Page<Expense> findByNameContaining(Long userId,String keyword, Pageable page);
	
	//SELECT * FROM EXPENSES WHERE USERID =? and DATE BETWEEN 'startDate' and 'endDate'
	Page<Expense> findByDateBetween (Long userId,Date startDate, Date endDate, Pageable page);
	
	//SELECT * FROM EXPENSES WHERE USERID=?
	Page<Expense> findByUserId(Long userId, Pageable page);
	
	//SELECT * FROM EXPENSES WHERE USERID=? AND ID=?
	Optional<Expense> findByUserIdAndId(Long userId, Long expenseId); 

}
