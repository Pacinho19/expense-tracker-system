package pl.pacinho.expensetrackersystem.expense.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.pacinho.expensetrackersystem.expense.controller.tools.HttpFileResponse;
import pl.pacinho.expensetrackersystem.expense.export.model.ExportFormat;
import pl.pacinho.expensetrackersystem.expense.export.service.ExpenseExportService;
import pl.pacinho.expensetrackersystem.expense.model.dto.ExpenseDto;
import pl.pacinho.expensetrackersystem.expense.model.entity.Expense;
import pl.pacinho.expensetrackersystem.expense.model.enums.Category;
import pl.pacinho.expensetrackersystem.expense.report.service.ExpensesMonthlyReportGenerator;
import pl.pacinho.expensetrackersystem.expense.service.ExpenseService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Validated
@RequiredArgsConstructor
@RequestMapping("/expense")
@RestController
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ExpenseExportService expenseExportService;
    private final ExpensesMonthlyReportGenerator expensesMonthlyReportGenerator;


    @GetMapping(params = {"!category", "!startDate", "!endDate"})
    ResponseEntity<List<ExpenseDto>> getExpense() {
        return ResponseEntity.ok(
                expenseService.findAll()
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<ExpenseDto> getExpense(@PathVariable("id") int id) {
        Optional<ExpenseDto> expenseOpt = expenseService.findById(id);
        return expenseOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    ResponseEntity<List<ExpenseDto>> findByCategoryOrDate(@RequestParam(value = "category", required = false) Category category,
                                                          @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                          @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(
                expenseService.findAllByCategoryOrDate(category, startDate, endDate)
        );
    }

    @PostMapping
    ResponseEntity<?> createExpense(@RequestBody ExpenseDto toCreate) {
        Expense save = expenseService.save(toCreate);
        if (save == null || save.getId() == null)
            return ResponseEntity.badRequest()
                    .build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(save.getId())
                .toUri();

        return ResponseEntity.created(
                location
        ).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<ExpenseDto> updateExpense(@PathVariable("id") int id,
                                             @RequestBody ExpenseDto toUpdate) {
        ExpenseDto updated = expenseService.update(id, toUpdate);
        if (updated == null)
            return ResponseEntity.notFound()
                    .build();

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteExpense(@PathVariable("id") int id) {
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/export")
    void export(@RequestParam(value = "format", defaultValue = "JSON") ExportFormat exportFormat,
                HttpServletResponse response) throws IOException {
        String content = expenseExportService.export(
                exportFormat,
                expenseService.findAll()
        );

        String outputFileName = UUID.randomUUID() + "_expenses." + exportFormat.name().toLowerCase();
        HttpFileResponse.build(content, outputFileName, response);
    }

    @GetMapping("/monthly-report/{year}/{month}")
    ResponseEntity<?> generateExpensesMonthlyReport(@PathVariable("year") @Min(1) int year,
                                                    @PathVariable("month") @Min(1) @Max(12) int month) {
        return ResponseEntity.ok(
                expensesMonthlyReportGenerator.generate(year, month)
        );
    }

}