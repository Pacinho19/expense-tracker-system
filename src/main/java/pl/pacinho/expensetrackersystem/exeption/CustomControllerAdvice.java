package pl.pacinho.expensetrackersystem.exeption;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.pacinho.expensetrackersystem.exeption.model.ErrorMessage;
import pl.pacinho.expensetrackersystem.exeption.utils.CustomExceptionMessageUtils;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        e.printStackTrace();

        return ResponseEntity.badRequest()
                .body(CustomExceptionMessageUtils.getEnumErrorMessage(e));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        e.printStackTrace();

        return ResponseEntity.badRequest()
                .body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptions(Exception e) {
        e.printStackTrace();

        return ResponseEntity.internalServerError()
                .body(ErrorMessage.INTERNAL_SERVER_ERROR.getText());
    }

}
