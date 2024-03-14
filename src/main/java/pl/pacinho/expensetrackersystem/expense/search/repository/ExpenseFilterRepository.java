package pl.pacinho.expensetrackersystem.expense.search.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pacinho.expensetrackersystem.expense.model.entity.Expense;
import pl.pacinho.expensetrackersystem.expense.search.model.ExpenseFilterDto;
import pl.pacinho.expensetrackersystem.expense.search.model.FieldFilterDto;
import pl.pacinho.expensetrackersystem.expense.search.tools.ExpensePredicateFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ExpenseFilterRepository {

    private final EntityManagerFactory entityManagerFactory;

    public List<Expense> findAll(ExpenseFilterDto expenseFilterDto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Expense> criteriaQuery = criteriaBuilder.createQuery(Expense.class);
        Root<Expense> expenseRoot = criteriaQuery.from(Expense.class);

        criteriaQuery.select(expenseRoot)
                .where(
                        criteriaBuilder.and(
                                createPredicates(expenseRoot, criteriaBuilder, expenseFilterDto.getFilters())
                        )
                );

        TypedQuery<Expense> query = entityManager.createQuery(criteriaQuery);
//        addParams(query, expenseFilterDto);

        List<Expense> result = query.getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        return result;
    }

    private Predicate[] createPredicates(Root<Expense> expenseRoot, CriteriaBuilder criteriaBuilder, List<FieldFilterDto> filters) {
        return filters.stream()
                .filter(filterDto -> filterDto.value() != null)
                .map(filterDto -> ExpensePredicateFactory.build(expenseRoot, criteriaBuilder, filterDto))
                .toArray(Predicate[]::new);
    }

    private void addParams(TypedQuery<Expense> query, ExpenseFilterDto expenseFilterDto) {
        expenseFilterDto.getFilters()
                .stream()
                .filter(filterDto -> filterDto.value() != null)
                .forEach(filterDto -> query.setParameter(filterDto.paramName(), filterDto.value()));
    }
}
