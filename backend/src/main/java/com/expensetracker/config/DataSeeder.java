package com.expensetracker.config;

import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public void run(String... args) {
        if (categoryRepository.count() == 0) {
            Category food = categoryRepository.save(new Category(null, "Food & Dining", "#FF6B6B", "🍔"));
            Category transport = categoryRepository.save(new Category(null, "Transport", "#4ECDC4", "🚗"));
            Category shopping = categoryRepository.save(new Category(null, "Shopping", "#45B7D1", "🛍️"));
            Category health = categoryRepository.save(new Category(null, "Health", "#96CEB4", "💊"));
            Category entertainment = categoryRepository.save(new Category(null, "Entertainment", "#FFEAA7", "🎬"));
            Category utilities = categoryRepository.save(new Category(null, "Utilities", "#DDA0DD", "💡"));

            LocalDate now = LocalDate.now();
            List<Expense> sampleExpenses = List.of(
                new Expense(null, "Swiggy - Biryani", new BigDecimal("280.00"), now.minusDays(1), food, "Dinner", Expense.PaymentMethod.UPI),
                new Expense(null, "Ola cab to office", new BigDecimal("180.00"), now.minusDays(1), transport, null, Expense.PaymentMethod.CARD),
                new Expense(null, "Amazon - Earphones", new BigDecimal("1299.00"), now.minusDays(2), shopping, "Boat earphones", Expense.PaymentMethod.CARD),
                new Expense(null, "Apollo pharmacy", new BigDecimal("450.00"), now.minusDays(3), health, "Monthly medicines", Expense.PaymentMethod.CASH),
                new Expense(null, "Netflix subscription", new BigDecimal("649.00"), now.minusDays(4), entertainment, null, Expense.PaymentMethod.CARD),
                new Expense(null, "Zomato - Dosa", new BigDecimal("120.00"), now.minusDays(5), food, "Breakfast", Expense.PaymentMethod.UPI),
                new Expense(null, "Electricity bill", new BigDecimal("1840.00"), now.minusDays(6), utilities, "TNEB monthly", Expense.PaymentMethod.UPI),
                new Expense(null, "Petrol fill", new BigDecimal("500.00"), now.minusDays(7), transport, null, Expense.PaymentMethod.CASH),
                new Expense(null, "Grocery - D-Mart", new BigDecimal("2350.00"), now.minusDays(8), food, "Weekly groceries", Expense.PaymentMethod.CARD),
                new Expense(null, "Gym membership", new BigDecimal("1200.00"), now.minusDays(10), health, "Monthly", Expense.PaymentMethod.UPI),
                new Expense(null, "Movie - PVR", new BigDecimal("540.00"), now.minusDays(12), entertainment, "Weekend outing", Expense.PaymentMethod.CARD),
                new Expense(null, "Dominos pizza", new BigDecimal("399.00"), now.minusDays(15), food, null, Expense.PaymentMethod.UPI),
                new Expense(null, "Bus pass", new BigDecimal("350.00"), now.minusDays(20), transport, "Monthly pass", Expense.PaymentMethod.CASH),
                new Expense(null, "Water bill", new BigDecimal("220.00"), now.minusDays(22), utilities, null, Expense.PaymentMethod.UPI),
                new Expense(null, "Flipkart - Shirt", new BigDecimal("799.00"), now.minusMonths(1).minusDays(2), shopping, null, Expense.PaymentMethod.CARD),
                new Expense(null, "Restaurant dinner", new BigDecimal("850.00"), now.minusMonths(1).minusDays(5), food, "Team outing", Expense.PaymentMethod.CARD),
                new Expense(null, "Rapido bike", new BigDecimal("90.00"), now.minusMonths(1).minusDays(8), transport, null, Expense.PaymentMethod.UPI),
                new Expense(null, "Doctor consultation", new BigDecimal("500.00"), now.minusMonths(1).minusDays(10), health, null, Expense.PaymentMethod.CASH),
                new Expense(null, "Spotify premium", new BigDecimal("119.00"), now.minusMonths(1).minusDays(15), entertainment, null, Expense.PaymentMethod.CARD),
                new Expense(null, "Internet bill", new BigDecimal("999.00"), now.minusMonths(1).minusDays(18), utilities, "BSNL broadband", Expense.PaymentMethod.UPI)
            );

            expenseRepository.saveAll(sampleExpenses);
        }
    }
}
