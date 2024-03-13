package pl.pacinho.expensetrackersystem.expense.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.pacinho.expensetrackersystem.expense.model.dto.ExpenseDto;
import pl.pacinho.expensetrackersystem.expense.model.entity.Expense;
import pl.pacinho.expensetrackersystem.expense.model.enums.Category;
import pl.pacinho.expensetrackersystem.expense.repository.ExpenseRepository;
import pl.pacinho.expensetrackersystem.expense.service.ExpenseService;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExpenseControllerE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ExpenseRepository expenseRepository;

    private String apiPath() {
        return "http://localhost:" + port;
    }

    @Test
    void httpInsertNewTaskShouldReturnLocationHeader() throws MalformedURLException {
        //given
        Integer maxId = expenseRepository.findAll().stream().map(Expense::getId).max(Integer::compareTo).orElse(0);
        Expense expense = Expense.builder()
                .name("Expense1")
                .category(Category.ENTERTAINMENT)
                .amount(BigDecimal.TEN)
                .date(LocalDate.now())
                .build();

        //when
        URI uri = testRestTemplate.postForLocation(apiPath() + "/expense", expense);

        //then
        assertThat(uri.toURL().toString(), equalTo(apiPath() + "/expense/" + (maxId + 1)));
    }

}