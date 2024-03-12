package pl.pacinho.expensetrackersystem.expense.model.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import pl.pacinho.expensetrackersystem.expense.model.dto.ExpenseDto;
import pl.pacinho.expensetrackersystem.expense.model.enums.Category;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@Getter
@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    private BigDecimal amount;

    private LocalDate date;

    @CreationTimestamp
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime updatedOn;

    public void modify(ExpenseDto toUpdate) {
        this.name = toUpdate.name();
        this.amount = toUpdate.amount();
        this.date = toUpdate.date();
        this.category = toUpdate.category();
    }
}
