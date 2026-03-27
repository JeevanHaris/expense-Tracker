package com.expensetracker.service;

import com.expensetracker.dto.ExpenseDTO;
import com.expensetracker.dto.SummaryDTO;
import com.expensetracker.model.Budget;
import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;
import com.expensetracker.repository.BudgetRepository;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAllByOrderByDateDesc();
    }

    public List<Expense> getExpensesByFilters(Long categoryId, LocalDate startDate, LocalDate endDate, String keyword) {
        if (keyword != null && !keyword.isBlank()) {
            return expenseRepository.searchByKeyword(keyword.trim());
        }
        if (categoryId != null && startDate != null && endDate != null) {
            return expenseRepository.findByCategoryIdAndDateBetweenOrderByDateDesc(categoryId, startDate, endDate);
        }
        if (categoryId != null) {
            return expenseRepository.findByCategoryIdOrderByDateDesc(categoryId);
        }
        if (startDate != null && endDate != null) {
            return expenseRepository.findByDateBetweenOrderByDateDesc(startDate, endDate);
        }
        return getAllExpenses();
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
    }

    public Expense createExpense(ExpenseDTO dto) {
        Expense expense = new Expense();
        mapDtoToExpense(dto, expense);
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Long id, ExpenseDTO dto) {
        Expense expense = getExpenseById(id);
        mapDtoToExpense(dto, expense);
        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public SummaryDTO getSummary() {
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();
        int lastMonth = currentMonth == 1 ? 12 : currentMonth - 1;
        int lastMonthYear = currentMonth == 1 ? currentYear - 1 : currentYear;

        BigDecimal totalThisMonth = expenseRepository.sumByMonthAndYear(currentMonth, currentYear);
        BigDecimal totalLastMonth = expenseRepository.sumByMonthAndYear(lastMonth, lastMonthYear);
        BigDecimal totalThisYear = expenseRepository.sumByYear(currentYear);
        long countThisMonth = expenseRepository.countByMonthAndYear(currentMonth, currentYear);

        // Category breakdown
        List<Category> categories = categoryRepository.findAll();
        List<SummaryDTO.CategorySummary> categoryBreakdown = categories.stream().map(cat -> {
            BigDecimal total = expenseRepository.sumByCategoryAndMonthAndYear(cat.getId(), currentMonth, currentYear);
            List<Expense> expenses = expenseRepository.findByCategoryIdAndDateBetweenOrderByDateDesc(
                    cat.getId(),
                    LocalDate.of(currentYear, currentMonth, 1),
                    now);
            Optional<Budget> budget = budgetRepository.findByCategoryIdAndMonthAndYear(cat.getId(), currentMonth, currentYear);
            return new SummaryDTO.CategorySummary(
                    cat.getName(), cat.getColor(), total, expenses.size(),
                    budget.map(Budget::getMonthlyLimit).orElse(null)
            );
        }).filter(cs -> cs.getTotal().compareTo(BigDecimal.ZERO) > 0).collect(Collectors.toList());

        // Monthly totals for last 6 months
        List<SummaryDTO.MonthlyTotal> monthlyTotals = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {
            LocalDate d = now.minusMonths(i);
            BigDecimal total = expenseRepository.sumByMonthAndYear(d.getMonthValue(), d.getYear());
            String label = Month.of(d.getMonthValue()).getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + d.getYear();
            monthlyTotals.add(new SummaryDTO.MonthlyTotal(label, total));
        }

        // Recent expenses
        List<SummaryDTO.RecentExpense> recentExpenses = expenseRepository.findTop5ByOrderByDateDesc().stream()
                .map(e -> new SummaryDTO.RecentExpense(
                        e.getId(), e.getTitle(), e.getAmount(),
                        e.getDate().toString(),
                        e.getCategory() != null ? e.getCategory().getName() : "Uncategorized",
                        e.getCategory() != null ? e.getCategory().getColor() : "#888888"
                )).collect(Collectors.toList());

        return new SummaryDTO(totalThisMonth, totalLastMonth, totalThisYear, countThisMonth,
                categoryBreakdown, monthlyTotals, recentExpenses);
    }

    private void mapDtoToExpense(ExpenseDTO dto, Expense expense) {
        expense.setTitle(dto.getTitle());
        expense.setAmount(dto.getAmount());
        expense.setDate(dto.getDate());
        expense.setNote(dto.getNote());

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            expense.setCategory(category);
        }

        if (dto.getPaymentMethod() != null) {
            expense.setPaymentMethod(Expense.PaymentMethod.valueOf(dto.getPaymentMethod().toUpperCase()));
        }
    }
}
