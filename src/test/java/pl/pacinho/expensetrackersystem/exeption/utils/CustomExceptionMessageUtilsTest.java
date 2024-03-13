package pl.pacinho.expensetrackersystem.exeption.utils;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomExceptionMessageUtilsTest {

    @Test
    void customizedEnumErrorMessageShouldBeReturnedWhenExceptionIsInstanceOfInvalidFormatExceptionAndMessageContainingSpecificEnumErrorText() {
        //given
        Exception invalidFormatException = new Exception(InvalidFormatException.from(null, "values accepted for Enum class: [AAA,BBB,CCC]", null, null));

        //when
        String enumErrorMessage = CustomExceptionMessageUtils.getEnumErrorMessage(invalidFormatException);

        //then
        assertThat(enumErrorMessage, not(equalTo(invalidFormatException.getMessage())));
        assertThat(enumErrorMessage, equalTo("Enum value should be: [AAA,BBB,CCC]"));
    }

    @Test
    void originalErrorMessageShouldBeReturnedWhenExceptionIsInstanceOfInvalidFormatExceptionAndMessageNotContainingSpecificEnumErrorText() {
        //given
        Exception invalidFormatException = new Exception(InvalidFormatException.from(null, "other error message", null, null));

        //when
        String enumErrorMessage = CustomExceptionMessageUtils.getEnumErrorMessage(invalidFormatException);

        //then
        assertThat(enumErrorMessage, equalTo(invalidFormatException.getMessage()));
    }

    @Test
    void originalErrorMessageShouldBeReturnedWhenExceptionIsNotInstanceOfInvalidFormatException() {
        //given
        Exception invalidFormatException = new Exception("Error message");

        //when
        String enumErrorMessage = CustomExceptionMessageUtils.getEnumErrorMessage(invalidFormatException);

        //then
        assertThat(enumErrorMessage, equalTo(invalidFormatException.getMessage()));
    }

    @Test
    void nullPointerExceptionShouldBeThrownWhenGivenExceptionIsNull() {
        //given
        Exception exception = null;

        //when
        //then
        assertThrows(NullPointerException.class, () -> CustomExceptionMessageUtils.getEnumErrorMessage(exception));
    }
}