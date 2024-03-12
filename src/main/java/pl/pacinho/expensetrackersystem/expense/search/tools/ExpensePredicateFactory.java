package pl.pacinho.expensetrackersystem.expense.search.tools;

import pl.pacinho.expensetrackersystem.expense.model.entity.Expense;
import pl.pacinho.expensetrackersystem.expense.search.model.FieldFilterDto;

import javax.persistence.criteria.*;

public class ExpensePredicateFactory {
    public static Predicate build(Root<Expense> expenseRoot, CriteriaBuilder criteriaBuilder, FieldFilterDto filterDto) {
        ParameterExpression parameter = criteriaBuilder.parameter(filterDto.value().getClass(), filterDto.paramName());
        Expression objectPath = expenseRoot.get(filterDto.name());

        switch (filterDto.predicateFunction()) {
            case EQUAL_TO -> {
                return criteriaBuilder.equal(objectPath, parameter);
            }
            case LESS_THAN_OR_EQUAL_TO -> {
                return criteriaBuilder.lessThanOrEqualTo(objectPath, parameter);
            }
            case GREATER_THAN_OR_EQUAL_TO -> {
                return criteriaBuilder.greaterThan(objectPath, parameter);
            }
        }

        return null;
    }
}
