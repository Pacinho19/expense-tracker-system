package pl.pacinho.expensetrackersystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

@SpringBootApplication
public class ExpenseTrackerSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerSystemApplication.class, args);
	}

}
