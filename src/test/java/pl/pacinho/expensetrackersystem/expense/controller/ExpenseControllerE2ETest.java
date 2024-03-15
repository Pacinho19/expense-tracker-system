package pl.pacinho.expensetrackersystem.expense.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.pacinho.expensetrackersystem.config.security.jwt.JwtAuth;
import pl.pacinho.expensetrackersystem.expense.model.entity.Expense;
import pl.pacinho.expensetrackersystem.expense.model.enums.Category;
import pl.pacinho.expensetrackersystem.expense.repository.ExpenseRepository;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URI;
import java.time.LocalDate;

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

    @Autowired
    private JwtAuth jwtAuth;

    private String apiPath() {
        return "http://localhost:" + port;
    }

    @Test
    void httpInsertNewTaskShouldReturnedHttpStatus401WhenAuthorizeHeaderNotExists() {
        //given
        Expense expense = Expense.builder()
                .name("Expense1")
                .category(Category.ENTERTAINMENT)
                .amount(BigDecimal.TEN)
                .date(LocalDate.now())
                .build();

        //when
        ResponseEntity<Object> responseEntity = testRestTemplate.postForEntity(apiPath() + "/expense", expense, Object.class);

        //then
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.UNAUTHORIZED));
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

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtAuth.generateToken("father"));
        HttpEntity<Expense> entity = new HttpEntity<>(expense, headers);

        //when
        URI uri = testRestTemplate.postForLocation(apiPath() + "/expense", entity);

        //then
        assertThat(uri.toURL().toString(), equalTo(apiPath() + "/expense/" + (maxId + 1)));
    }

}