package pl.pacinho.expensetrackersystem.expense.search.model;

public record FieldFilterDto(String name, String paramName, Object value, PredicateFunction predicateFunction) {
}
