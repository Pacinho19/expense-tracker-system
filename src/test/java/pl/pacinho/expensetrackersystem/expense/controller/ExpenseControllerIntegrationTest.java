package pl.pacinho.expensetrackersystem.expense.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.pacinho.expensetrackersystem.config.security.jwt.JwtAuth;
import pl.pacinho.expensetrackersystem.expense.model.dto.ExpenseDto;
import pl.pacinho.expensetrackersystem.expense.model.enums.Category;
import pl.pacinho.expensetrackersystem.expense.service.ExpenseService;

import java.math.BigDecimal;
import java.time.LocalDate;

@EnableAutoConfiguration(exclude = FlywayAutoConfiguration.class)
@ActiveProfiles("integration")
@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ExpenseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private JwtAuth jwtAuth;

    @Test
    void httpGetExpenseByIdShouldReturnedGivenExpense() throws Exception {
        //given
        String authToken = "Bearer " + jwtAuth.generateToken("user");
        ExpenseDto expense = new ExpenseDto("Expense1", Category.ENTERTAINMENT, BigDecimal.TEN, LocalDate.now());

        int id = expenseService.save(expense).getId();

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/expense/" + id)
                        .header("Authorization", authToken))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void httpGetExpenseByIdShouldReturnedHttpStatus403WhenAuthorizeHeaderNotExists() throws Exception {
        //given
        int id = 1;

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/expense/" + id))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void httpGetExpenseByIdShouldReturnedStatus404WhenExpenseWithGivenIdNotExists() throws Exception {
        //given
        String authToken = "Bearer " + jwtAuth.generateToken("user");
        int id = 1;

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/expense/" + id).header("Authorization", authToken))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}