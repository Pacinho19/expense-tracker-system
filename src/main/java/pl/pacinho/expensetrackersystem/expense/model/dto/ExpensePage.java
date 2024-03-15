package pl.pacinho.expensetrackersystem.expense.model.dto;

import java.util.List;

public record ExpensePage(List<ExpenseDto> expenses, long currentPage, long totalElements, long totalPages, long size) {
}
