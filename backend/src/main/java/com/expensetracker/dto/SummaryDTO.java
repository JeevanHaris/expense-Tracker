package com.expensetracker.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummaryDTO {

    private BigDecimal totalThisMonth;
    private BigDecimal totalLastMonth;
    private BigDecimal totalThisYear;
    private long expenseCountThisMonth;

    private List<CategorySummary> categoryBreakdown;
    private List<MonthlyTotal> monthlyTotals;
    private List<RecentExpense> recentExpenses;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CategorySummary {
        private String category;
        private String color;
        private BigDecimal total;
        private long count;
        private BigDecimal budgetLimit;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MonthlyTotal {
        private String month;
        private BigDecimal total;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RecentExpense {
        private Long id;
        private String title;
        private BigDecimal amount;
        private String date;
        private String category;
        private String color;
    }
}
