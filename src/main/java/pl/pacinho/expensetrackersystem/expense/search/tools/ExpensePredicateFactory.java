package pl.pacinho.expensetrackersystem.expense.search.tools;

import pl.pacinho.expensetrackersystem.expense.model.entity.Expense;
import pl.pacinho.expensetrackersystem.expense.search.model.FieldFilterDto;

import javax.persistence.criteria.*;
import java.time.LocalDate;

public class ExpensePredicateFactory {
    public static Predicate build(Root<Expense> expenseRoot, CriteriaBuilder criteriaBuilder, FieldFilterDto filterDto) {
        Expression objectPath = expenseRoot.get(filterDto.name());

        switch (filterDto.predicateFunction()) {
            case EQUAL_TO -> {
                return criteriaBuilder.equal(objectPath, filterDto.value());
            }
            case CONTAINS -> {
                return criteriaBuilder.like(criteriaBuilder.upper(objectPath), "%" + ((String) filterDto.value()).toUpperCase() + "%");
            }
            case DATE_LESS_THAN_OR_EQUAL_TO -> {
                return criteriaBuilder.lessThanOrEqualTo(objectPath, (LocalDate) filterDto.value());
            }
            case DATE_GREATER_THAN_OR_EQUAL_TO -> {
                return criteriaBuilder.greaterThanOrEqualTo(objectPath, (LocalDate) filterDto.value());
            }
        }

        return null;
    }
}
