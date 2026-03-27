package com.expensetracker.repository;

import com.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findAllByOrderByDateDesc();

    List<Expense> findByCategoryIdOrderByDateDesc(Long categoryId);

    List<Expense> findByDateBetweenOrderByDateDesc(LocalDate startDate, LocalDate endDate);

    List<Expense> findByCategoryIdAndDateBetweenOrderByDateDesc(
            Long categoryId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT e FROM Expense e WHERE " +
           "LOWER(e.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(e.note) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "ORDER BY e.date DESC")
    List<Expense> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e " +
           "WHERE MONTH(e.date) = :month AND YEAR(e.date) = :year")
    BigDecimal sumByMonthAndYear(@Param("month") int month, @Param("year") int year);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE YEAR(e.date) = :year")
    BigDecimal sumByYear(@Param("year") int year);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e " +
           "WHERE e.category.id = :categoryId AND " +
           "MONTH(e.date) = :month AND YEAR(e.date) = :year")
    BigDecimal sumByCategoryAndMonthAndYear(
            @Param("categoryId") Long categoryId,
            @Param("month") int month,
            @Param("year") int year);

    @Query("SELECT COUNT(e) FROM Expense e WHERE MONTH(e.date) = :month AND YEAR(e.date) = :year")
    long countByMonthAndYear(@Param("month") int month, @Param("year") int year);

    @Query("SELECT e FROM Expense e ORDER BY e.date DESC LIMIT 5")
    List<Expense> findTop5ByOrderByDateDesc();
}
